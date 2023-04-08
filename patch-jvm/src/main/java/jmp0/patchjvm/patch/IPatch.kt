package jmp0.patchjvm.patch

import jmp0.patchjvm.library.CLibrary

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/4/7
 */
interface IPatch {
    val cLibrary: CLibrary
    val jvmLibraryName:String
    fun patch()
}