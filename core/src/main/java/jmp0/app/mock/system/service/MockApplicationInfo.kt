package jmp0.app.mock.system.service

import android.content.pm.ApplicationInfo
import jmp0.app.DbgContext
import jmp0.app.mock.ClassReplaceTo

@ClassReplaceTo("")
class MockApplicationInfo : ApplicationInfo() {
    init {
        val uuid = this::class.java.getDeclaredField("xxUuid").get(null) as String
        val env = DbgContext.getAndroidEnvironment(uuid)
        packageName = env!!.apkFile.packageName
        dataDir = env.apkFile.privateDir.canonicalPath
        nativeLibraryRootDir = env.apkFile.nativeLibraryDirRoot.canonicalPath
        nativeLibraryDir = env.apkFile.nativeLibraryDir.canonicalPath
        processName = env.processName
        publicSourceDir = env.apkFile.copyApkFile.canonicalPath
        requiresSmallestWidthDp = 0
        resourceDirs = arrayOf(publicSourceDir)
        scanPublicSourceDir = env.apkFile.dir.canonicalPath
        scanSourceDir = env.apkFile.dir.canonicalPath
        seinfo = "default:targetSdkVersion=23"
        sourceDir = env.apkFile.copyApkFile.canonicalPath
        targetSdkVersion = 23
        taskAffinity = packageName
    }
}