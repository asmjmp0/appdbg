package jmp0.app.mock.system.service

import android.content.pm.InstrumentationInfo
import jmp0.app.DbgContext
import jmp0.app.mock.ClassReplaceTo

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/14
 */
@ClassReplaceTo("")
class MockInstrumentationInfo:InstrumentationInfo() {
    init {
        val uuid = this::class.java.getDeclaredField("xxUuid").get(null) as String
        val env = DbgContext.getAndroidEnvironment(uuid)!!

        targetPackage = env.apkFile.packageName
        sourceDir = env.apkFile.copyApkFile.canonicalPath
        publicSourceDir = env.apkFile.copyApkFile.canonicalPath
        dataDir = env.apkFile.privateDir.canonicalPath
        nativeLibraryDir = env.apkFile.nativeLibraryDir.canonicalPath
        handleProfiling = false
        functionalTest = false

    }
}