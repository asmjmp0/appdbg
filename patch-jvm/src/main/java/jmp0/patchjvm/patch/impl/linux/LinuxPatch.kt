package jmp0.patchjvm.patch.impl.linux

import jmp0.patchjvm.library.CLibrary
import jmp0.patchjvm.patch.IPatch
import java.io.File

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/4/7
 */
class LinuxPatch(override val cLibrary: CLibrary, override val jvmLibraryName: String = "libjvm.so") : IPatch {
    override fun patch() {
        TODO("Not yet implemented")
    }
}