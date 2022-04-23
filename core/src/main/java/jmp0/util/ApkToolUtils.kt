package jmp0.util

import jmp0.conf.CommonConf
import java.io.File

object ApkToolUtils {

    fun releaseApkFile(apkFile: File): Int {
        val dirName = apkFile.name
        val tempDir = File(CommonConf.workDir+File.separator+CommonConf.tempDirName).apply { if (!exists()) mkdir() }
        val process = CommandUtils.exec(arrayOf("java","-jar","${CommonConf.workDir}${File.separator}${CommonConf.tempDirName}${File.separator}${CommonConf.apktoolResourcePath}.jar",
            "d","-s","-f",apkFile.canonicalPath,"-o",tempDir.canonicalPath+File.separator+dirName))
        CommandUtils.readProcessPipe(process)
        return process.waitFor()
    }
}