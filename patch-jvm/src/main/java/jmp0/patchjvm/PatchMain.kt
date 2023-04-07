package jmp0.patchjvm

import com.sun.jna.Native
import com.sun.jna.Platform
import jmp0.patchjvm.impl.linux.LinuxPatch
import jmp0.patchjvm.impl.osx.OSXPatch
import jmp0.patchjvm.impl.windows.WindowsPatch
import jmp0.patchjvm.library.CLibrary

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/4/7
 */
class PatchMain {
    private val cl = Native.load(if (Platform.isWindows()) "msvcrt" else "c", CLibrary::class.java) as CLibrary

    fun patch() =
        when(Platform.getOSType()){
            Platform.MAC-> OSXPatch()
            Platform.WINDOWS -> WindowsPatch()
            Platform.LINUX -> LinuxPatch()
            else -> throw Exception("patch jvm ${Platform.getOSType()} not support")
        }.patch(cl)
}