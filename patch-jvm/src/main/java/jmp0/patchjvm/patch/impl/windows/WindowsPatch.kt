package jmp0.patchjvm.patch.impl.windows

import jmp0.patchjvm.library.CLibrary
import jmp0.patchjvm.patch.IPatch
import java.io.File

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/4/7
 */
class WindowsPatch(override val cLibrary: CLibrary, override val jvmLibraryName: String = "jvm.dll") : IPatch {
    override fun patch() {
        TODO("Not yet implemented")
    }
}