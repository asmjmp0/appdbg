package jmp0.apk

import jmp0.conf.CommonConf
import jmp0.util.ApkToolUtils
import jmp0.util.DexUtils
import org.apache.log4j.Logger
import java.io.File

class ApkFile(private val apkFile: File,force:Boolean = false) {
    private val logger = Logger.getLogger(javaClass)
    val dir = File("${CommonConf.workDir}${File.separator}${CommonConf.tempDirName}${File.separator}${apkFile.name}")
    val classesDir = File(dir,"classes")
    private var manifest:ManifestAnalyse
    lateinit var copyApkFile:File

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
            releaseApkFile()
            releaseDex()
            copyApkFile = copyApk()
        }else{
            if (force){
                dir.deleteRecursively()
                releaseApkFile()
                logger.debug("force enabled, delete the dir")
            }else {
                logger.debug("apk dir exists, just use it")
            }
            releaseDex()
            copyApkFile = copyApk()
        }
        manifest = ManifestAnalyse(File(dir,"AndroidManifest.xml"))
        packageName = manifest.packaeName
        getResources()

    }

    private fun copyApk() =
        File(dir,apkFile.name).apply { writeBytes(apkFile.readBytes()) }


    private fun getResources(){
    }

    private fun releaseApkFile(){
        val ret = ApkToolUtils.releaseApkFile(apkFile)
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