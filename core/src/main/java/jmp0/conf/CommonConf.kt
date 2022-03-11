package jmp0.conf

import java.io.File
import java.io.File.separator

object CommonConf {
    const val tempDirName = "temp"
    const val toolDirName = "tools"

    //framework
    val frameworkFileName = "libs${separator}android-all-9-robolectric.jar"
    const val frameworkDirName = "framework"

    //system
    val mockAndroidJar = "mock-android/build/libs/mock-android.jar".replace("/",File.separator)
    val mockSystemClass = "core/src/main/java/jmp0/app/clazz/system".replace("/",File.separator)
    val userSystemClassPackageName= "jmp0/app/clazz/system".replace('/','.')
}