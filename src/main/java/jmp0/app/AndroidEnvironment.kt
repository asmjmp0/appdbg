package jmp0.app

import com.googlecode.d2j.Field
import com.googlecode.d2j.dex.Dex2jar
import jmp0.conf.CommonConf
import jmp0.dex.DexUtils
import jmp0.dex.XDexClassLoader
import java.io.File
import java.util.zip.ZipFile

class AndroidEnvironment {
    val loader = XDexClassLoader.instance

    init {
        //create temp dir
        File(CommonConf.tempDirName).apply { if (!exists()) mkdir() }
        checkAndReleaseFramework()
    }

    private fun checkAndReleaseFramework(){
        val frameworkDir = File("${CommonConf.tempDirName}${File.separator}${CommonConf.frameworkDirName}")
        if(!frameworkDir.exists()){
            frameworkDir.mkdir()
            DexUtils.releaseApkClassFile(File(CommonConf.frameworkFileName),frameworkDir)
        }
    }

}