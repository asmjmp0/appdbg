package jmp0.conf

import java.io.File

object CommonConf {
    const val tempDirName = "temp"
    const val copyDirName = "copy"
    var workDir: String = System.getProperty("user.dir")

    //framework
    val frameworkFileName = "android-all-6.0.0_r1-robolectric-0"
    const val frameworkDirName = "framework"

    const val appJarDir = "tempLibs"
    const val apktoolName = "apktool_2.6.1"
    const val toolsName = "mtools"
    val apktoolResourcePath = "${toolsName}${File.separator}${apktoolName}"

    val system by lazy {
        val name = System.getProperty("os.name").lowercase()
        if(name.contains("windows")) "windows"
        else "unix like"
    }

    object Mock{
        //system
        const val userSystemClassPackageName= "jmp0.app.mock.system"

        //native
        const val mockNativeClassPackageName= "jmp0.app.mock.ntv"

        //method
        const val mockMethodClassPackageName= "jmp0.app.mock.method"
    }
}