package jmp0.util

import jmp0.conf.CommonConf
import java.io.File

object ApkToolUtils {
    private const val version = "2.6.1"
    private const val apktoolName = "apktool_$version.jar"

    fun releaseApkFile(apkFile: File): Int {
        val dirName = apkFile.name
        val tempDir = File(CommonConf.tempDirName).apply { if (!exists()) mkdir() }
        val process = CommandUtils.exec(arrayOf("java","-jar","${CommonConf.toolDirName}${File.separator}${apktoolName}",
            "d","-s","-f",apkFile.canonicalPath,"-o",tempDir.canonicalPath+File.separator+dirName))
        CommandUtils.readProcessPipe(process)
        return process.waitFor()
    }
}