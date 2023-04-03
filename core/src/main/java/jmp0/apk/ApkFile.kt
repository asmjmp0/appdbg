package jmp0.apk

import jmp0.apk.config.DefaultApkConfig
import jmp0.apk.config.IApkConfig
import jmp0.conf.CommonConf
import jmp0.util.ApkToolUtils
import jmp0.util.DexUtils
import net.dongliu.apk.parser.ApkFile
import net.dongliu.apk.parser.ApkParsers
import net.dongliu.apk.parser.bean.CertificateMeta
import org.apache.log4j.Logger
import java.io.File
import java.io.InputStream
import java.util.LinkedList
import java.util.StringJoiner
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class ApkFile(private val stream:InputStream,private val name:String,apkConfig: IApkConfig = DefaultApkConfig()) {

    constructor(apkFile: File,apkConfig: IApkConfig = DefaultApkConfig()):this(apkFile.inputStream(),apkFile.name,apkConfig)

    private val logger = Logger.getLogger(javaClass)
    private val copyDir = File("${CommonConf.workDir}${File.separator}${CommonConf.tempDirName}${File.separator}${CommonConf.copyDirName}").apply {
        if (!exists()) mkdirs()
    }
    val dir = File("${CommonConf.workDir}${File.separator}${CommonConf.tempDirName}${File.separator}${name}")
    val classesDir = File(dir,"classes")
    val copyApkFile:File

    var packageName:String
    var privateDir:File = File(dir,"env").apply { if (!exists()) mkdir() }
    var commonDatabasesDir:File = File(dir,"databases").apply { if(!exists()) mkdir() }
    var nativeLibraryDirRoot:File = File(dir,"lib").apply { if (!exists()) mkdir() }
    var nativeLibraryDir:File = File(nativeLibraryDirRoot,"armeabi-v7a").apply { if (!exists()) mkdir() }
    var assetsDir = File(dir,"assets")
    var arscFileName = "resources.arsc"
    var sharedPreferencesDir = File(dir,"sharedpreferences").apply { if (!exists()) mkdir() }
    var decompileSourceDir:File = File(dir,"decompile_source").apply { if (!exists()) mkdir() }
    private val dongliuApkFile:net.dongliu.apk.parser.ApkFile

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
            releaseDex(apkConfig.jarWithDebugInfo())
            DexUtils.generateDevelopJar(classesDir,apkConfig.generateJarFile())
        }else{
            if (apkConfig.forceDecompile()){
                copyApkFile = copyApk()
                logger.debug("force enabled, delete the dir")
                dir.deleteRecursively()
                releaseApkFile()
                releaseDex(apkConfig.jarWithDebugInfo())
                DexUtils.generateDevelopJar(classesDir,apkConfig.generateJarFile())
            }else {
                copyApkFile = File(copyDir,name)
                logger.debug("apk dir exists, just use it")
            }
        }
        getResources()
        dongliuApkFile = ApkFile(copyApkFile)
        packageName = dongliuApkFile.apkMeta.packageName

    }
    fun getVersionCode(): Long = dongliuApkFile.apkMeta.versionCode

    fun getVersionName():String = dongliuApkFile.apkMeta.versionName
    fun getSignatures(): Array<CertificateMeta> {
        val certificateMetas = LinkedList<CertificateMeta>()
        dongliuApkFile.apkSingers.forEach {
            certificateMetas.addAll(it.certificateMetas)
        }
        return certificateMetas.toTypedArray()
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

    private fun releaseDex(generateSourceLine:Boolean){
        val list = LinkedList<File>();
        classesDir.apply { if(!exists()) mkdir() else return }
        dir.listFiles()!!.forEach {
            if (it.name.endsWith(".dex")){
                list.add(it)
            }
        }
        releaseDexFileImpl(list.toTypedArray(),generateSourceLine)
    }

    private fun releaseDexFileImpl(dexArr:Array<File>,generateSourceLine:Boolean){
        val service = Executors.newFixedThreadPool(1)
        dexArr.forEach {
            service.execute {
                logger.info("$it has submitted to decompiler")
                DexUtils.releaseDexClassFile(it,classesDir,generateSourceLine)
                logger.info("$it decompile completely")
            }
        }
        service.shutdown()
        service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS)
    }
}