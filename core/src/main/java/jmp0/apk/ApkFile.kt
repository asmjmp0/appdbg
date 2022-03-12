package jmp0.apk

import jmp0.conf.CommonConf
import jmp0.util.ApkToolUtils
import jmp0.util.DexUtils
import org.apache.log4j.Logger
import java.io.File

class ApkFile(private val apkFile: File,force:Boolean = false) {
    private val logger = Logger.getLogger(javaClass)
    private val dir = File("${CommonConf.tempDirName}${File.separator}${apkFile.name}")
    val classesDir = File(dir,"classes")
    private lateinit var manifest:ManifestAnalyse
    private lateinit var copyApkFile:File

    init {
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
            copyApk()
        }
        manifest = ManifestAnalyse(File(dir,"AndroidManifest.xml"))
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