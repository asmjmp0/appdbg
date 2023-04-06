package jmp0.app.pathjvm

import com.sun.jna.Library
import com.sun.jna.Pointer

interface CLibrary:Library {
    fun printf(string: String, vararg args:Any)
    fun dlopen(name:String,mode:Int):Pointer
//    fun dladdr(handle:Pointer)
    fun dlclose(handle:Pointer)
}