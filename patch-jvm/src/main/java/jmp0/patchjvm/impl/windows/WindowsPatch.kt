package jmp0.patchjvm.impl.windows

import jmp0.patchjvm.library.CLibrary
import jmp0.patchjvm.IPatch

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/4/7
 */
class WindowsPatch(override val jvmLibraryName: String = "jvm.dll") :IPatch {
    override fun patch(cLibrary: CLibrary) {
        TODO("Not yet implemented")
    }
}