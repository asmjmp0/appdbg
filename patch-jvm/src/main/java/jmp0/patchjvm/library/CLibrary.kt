package jmp0.patchjvm.library

import com.sun.jna.Library
import com.sun.jna.Pointer

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/4/7
 */
interface CLibrary:Library {
    companion object{
        val RTLD_DI_SERINFO = 4

        val PROT_READ = 0x1                /* Page can be read.  */
        val PROT_WRITE = 0x2               /* Page can be written.  */
        val PROT_EXEC = 0x4                /* Page can be executed.  */
        val PROT_NONE = 0x0
    }
    fun printf(string: String, vararg args:Any)
    fun dlopen(name:String,mode:Int):Pointer
    fun dlsym(handle:Pointer,name: String):Pointer
    fun dladdr(handle:Pointer,dlInfo: Pointer)
    fun dlclose(handle:Pointer)

    fun setuid(id:Int):Int

    fun mprotect(adr:Pointer,len:Int,port:Int):Int
}