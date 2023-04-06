package jmp0.patchjvm

import com.sun.jna.Library
import com.sun.jna.Pointer

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/4/7
 */
interface CLibrary:Library {
    fun printf(string: String, vararg args:Any)
    fun dlopen(name:String,mode:Int):Pointer
//    fun dladdr(handle:Pointer)
    fun dlclose(handle:Pointer)
}