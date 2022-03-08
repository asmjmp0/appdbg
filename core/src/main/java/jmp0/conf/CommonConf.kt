package jmp0.conf

import java.io.File
import java.io.File.separator

object CommonConf {
    const val tempDirName = "temp"
    const val toolDirName = "tools"

    //framework
    val frameworkFileName = "libs${separator}android-all-9-robolectric.jar"
    const val frameworkDirName = "framework"
}