package jmp0.patchjvm.library

import com.sun.jna.Library
import com.sun.jna.Pointer

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/4/7
 */
interface CLibrary:Library {
    companion object{
        const val PROT_READ = 0x1                /* Page can be read.  */
        const val PROT_WRITE = 0x2               /* Page can be written.  */
        const val PROT_EXEC = 0x4                /* Page can be executed.  */
    }
    fun malloc(size: Int):Pointer
    fun free(p:Pointer)
    fun mprotect(adr:Pointer,len:Int,port:Int):Int
}