package jmp0.patchjvm

import com.sun.jna.Native
import com.sun.jna.Platform
import jmp0.patchjvm.patch.impl.linux.LinuxPatch
import jmp0.patchjvm.patch.impl.osx.OSXPatch
import jmp0.patchjvm.patch.impl.windows.WindowsPatch
import jmp0.patchjvm.library.CLibrary
import java.io.File

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/4/7
 */
class PatchMain(private val workDir:File) {
    private val cl = Native.load(if (Platform.isWindows()) "msvcrt" else "c", CLibrary::class.java) as CLibrary

    fun patch() =
        when(Platform.getOSType()){
            Platform.MAC-> OSXPatch(workDir,cl)
            Platform.WINDOWS -> WindowsPatch(cl)
            Platform.LINUX -> LinuxPatch(cl)
            else -> throw Exception("patch jvm ${Platform.getOSType()} not support")
        }.patch()
}