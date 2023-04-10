package jmp0.patchjvm.patch.impl.osx.structure

import com.sun.jna.Structure
import com.sun.jna.Structure.FieldOrder

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/4/7
 */
@FieldOrder("magic","cputype","cpusubtype","filetype","ncmds","sizeofcmds","flags","reserved")
class MachHeader64:Structure() {
    @JvmField var magic: Int? = null
    @JvmField var cputype: Int? = null
    @JvmField var cpusubtype: Int? = null
    @JvmField var filetype: Int? = null
    @JvmField var ncmds: Int = 0
    @JvmField var sizeofcmds: Int? = null
    @JvmField var flags: Int? = null
    @JvmField var reserved: Int? = null
}