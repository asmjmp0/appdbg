package jmp0.conf

import java.io.File
import java.io.File.separator

object CommonConf {
    const val tempDirName = "temp"
    const val toolDirName = "tools"

    const val stdout = "stdout"

    //framework
    val frameworkFileName = "libs${separator}android${separator}framework.jar"
    const val frameworkDirName = "framework"
}