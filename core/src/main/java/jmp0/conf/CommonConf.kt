package jmp0.conf

import java.io.File
import java.io.File.separator

object CommonConf {
    const val tempDirName = "temp"
    const val toolDirName = "tools"

    //framework
    val frameworkFileName = "libs${separator}android-all-6.0.0_r1-robolectric-0.jar"
    const val frameworkDirName = "framework"

    //system
    val mockAndroidJar = "mock-android/build/libs/mock-android.jar".replace("/",separator)
    val mockSystemClass = "core/src/main/java/jmp0/app/mock/system".replace("/",separator)
    val userSystemClassPackageName= "jmp0/app/mock/system".replace('/','.')
}