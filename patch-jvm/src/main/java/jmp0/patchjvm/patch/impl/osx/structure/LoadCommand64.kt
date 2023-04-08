package jmp0.patchjvm.patch.impl.osx.structure

import com.sun.jna.Structure
import com.sun.jna.Structure.FieldOrder

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/4/7
 */
@FieldOrder("cmd","cmdsize")
class LoadCommand64:Structure(){
    @JvmField var cmd:Int? = null
    @JvmField var cmdsize:Int? = null
}