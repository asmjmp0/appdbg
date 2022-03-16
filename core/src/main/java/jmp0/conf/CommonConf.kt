package jmp0.conf

import java.io.File
import java.io.File.separator

object CommonConf {
    const val tempDirName = "temp"
    const val toolDirName = "tools"

    //framework
    val frameworkFileName = "libs${separator}android-all-6.0.0_r1-robolectric-0.jar"
    const val frameworkDirName = "framework"

    //java
    const val javaPath = "temp/${frameworkDirName}/java"
    const val javaxPath = "temp/${frameworkDirName}/javax"

    object Mock{
        //system
        val mockSystemClass = "core/src/main/java/jmp0/app/mock/system".replace("/",separator)
        const val userSystemClassPackageName= "jmp0.app.mock.system"

        //native
        val mockNativeClass = "core/src/main/java/jmp0/app/mock/ntv".replace("/",separator)
        const val mockNativeClassPackageName= "jmp0.app.mock.ntv"

        //method
        val mockMethodClass = "core/src/main/java/jmp0/app/mock/method".replace("/",separator)
        const val mockMethodClassPackageName= "jmp0.app.mock.method"
    }
}