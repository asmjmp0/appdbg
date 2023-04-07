package jmp0.patchjvm.impl.osx

import jmp0.patchjvm.impl.osx.structure.MachHeader64
import jmp0.patchjvm.library.CLibrary

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/4/7
 */
interface DyldLibrary:CLibrary {
    fun _dyld_image_count():Int

    fun _dyld_get_image_name(idx:Int):String

    fun _dyld_get_image_header(idx: Int): MachHeader64

}