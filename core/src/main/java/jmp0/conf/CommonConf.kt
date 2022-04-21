package jmp0.conf

object CommonConf {
    const val tempDirName = "temp"

    //framework
    val frameworkFileName = "android-all-6.0.0_r1-robolectric-0"
    const val frameworkDirName = "framework"

    const val appJarDir = "tempLibs"

    const val apktoolName = "apktool_2.6.1"
    const val toolsName = "mtools"
    const val apktoolResourcePath = "${toolsName}/${apktoolName}"

    object Mock{
        //system
        const val userSystemClassPackageName= "jmp0.app.mock.system"

        //native
        const val mockNativeClassPackageName= "jmp0.app.mock.ntv"

        //method
        const val mockMethodClassPackageName= "jmp0.app.mock.method"
    }
}