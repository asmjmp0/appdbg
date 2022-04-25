package jmp0.apk

import jmp0.conf.CommonConf
import jmp0.util.ApkToolUtils
import jmp0.util.DexUtils
import org.apache.log4j.Logger
import java.io.File
import java.io.InputStream

class ApkFile(private val stream:InputStream,private val name:String,force: Boolean,private val generateJar:Boolean = false) {

    constructor(apkFile: File,force:Boolean = false,generateJar:Boolean = false):this(apkFile.inputStream(),apkFile.name,force,generateJar)

    private val logger = Logger.getLogger(javaClass)
    private val copyDir = File("${CommonConf.workDir}${File.separator}${CommonConf.tempDirName}${File.separator}${CommonConf.copyDirName}").apply {
        if (!exists()) mkdirs()
    }
    val dir = File("${CommonConf.workDir}${File.separator}${CommonConf.tempDirName}${File.separator}${name}")
    val classesDir = File(dir,"classes")
    private var manifest:ManifestAnalyse
    val copyApkFile:File

    var packageName:String
    var privateDir:File = File(dir,"env").apply { if (!exists()) mkdir() }
    var commonDatabasesDir:File = File(dir,"databases").apply { if(!exists()) mkdir() }
    var nativeLibraryDirRoot:File = File(dir,"lib").apply { if (!exists()) mkdir() }
    var nativeLibraryDir:File = File(nativeLibraryDirRoot,"armeabi-v7a").apply { if (!exists()) mkdir() }
    var assetsDir = File(dir,"assets")
    var arscFileName = "resources.arsc"

    init {
        //release apktool
        val toolsDir = File(CommonConf.workDir+File.separator+CommonConf.tempDirName,CommonConf.toolsName)
        if (!toolsDir.exists()){
            toolsDir.mkdirs()
        }
        val apktoolFile = File(toolsDir,CommonConf.apktoolName+".jar")
        if (!apktoolFile.exists()){
            apktoolFile.createNewFile()
            val bytes = ClassLoader.getSystemClassLoader().getResource(CommonConf.toolsName+'/'+CommonConf.apktoolName)!!.openStream().readBytes()
            apktoolFile.writeBytes(bytes)
        }
        //release apk file
        if (!dir.exists()){
            copyApkFile = copyApk()
            releaseApkFile()
            releaseDex()
        }else{
            if (force){
                copyApkFile = copyApk()
                dir.deleteRecursively()
                releaseApkFile()
                logger.debug("force enabled, delete the dir")
            }else {
                copyApkFile = File(copyDir,name)
                logger.debug("apk dir exists, just use it")
            }
            releaseDex()
        }
        DexUtils.generateDevelopJar(classesDir,generateJar)
        manifest = ManifestAnalyse(File(dir,"AndroidManifest.xml"))
        packageName = manifest.packaeName
        getResources()

    }

    private fun copyApk() =
        File(copyDir,name).apply {
            if (!exists()) createNewFile()
            writeBytes(stream.readBytes())
        }


    private fun getResources(){
    }

    private fun releaseApkFile(){
        val ret = ApkToolUtils.releaseApkFile(copyApkFile)
        if (ret!=0) {
            logger.error("apkfile decoding error")
            throw Exception("apkfile decoding error")
        }
    }

    private fun releaseDex(){
        classesDir.apply { if(!exists()) mkdir() else return }
        dir.listFiles()!!.forEach {
            if (it.name.endsWith(".dex")){
                logger.debug("release ${it.name} ...")
                DexUtils.releaseDexClassFile(it,classesDir)
            }
        }
    }
}