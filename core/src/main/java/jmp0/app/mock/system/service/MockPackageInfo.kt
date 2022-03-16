package jmp0.app.mock.system.service

import android.content.pm.PackageInfo
import android.os.Build.VERSION_CODES
import jmp0.app.mock.annotations.ClassReplaceTo

@ClassReplaceTo("")
class MockPackageInfo:PackageInfo() {
    init {
        val mApplicationInfo = MockApplicationInfo()
        activities = null
        baseRevisionCode = 0
        versionCode = VERSION_CODES.LOLLIPOP
        versionName = "1.0"
        configPreferences = null
        coreApp = false
        packageName = mApplicationInfo.packageName
        applicationInfo = mApplicationInfo
    }
}