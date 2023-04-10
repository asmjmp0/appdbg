package jmp0.patchjvm.patch.impl.windows

import com.sun.jna.Library
import com.sun.jna.Pointer
import jmp0.patchjvm.patch.impl.windows.structure.IMAGE_SECTION_HEADERStructure

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/4/7
 */
interface Kernel32Library:Library {
    companion object{
        const val PAGE_EXECUTE_READWRITE = 0x40
    }
    fun GetModuleHandleA(str: String):Pointer
    fun VirtualProtect(lpAddress:Pointer,dwSize:Int,flNewProtect:Int,lpflOldProtect:Pointer?):Boolean
}