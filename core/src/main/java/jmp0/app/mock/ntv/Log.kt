package jmp0.app.mock.ntv

import jmp0.app.mock.annotations.NativeHookClass

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/8
 */
@NativeHookClass("android.util.Log")
object Log {

    @JvmStatic
    fun println_native(uuid: String,a: Int,b:Int,tag:String,content:String):Int{
        println("android log [$tag] $content")
        return 1
    }

    @JvmStatic
    fun logger_entry_max_payload_native(uuid: String):Int{
        return 1024
    }

    @JvmStatic
    fun isLoggable(uuid: String,tag:String,a:Int):Boolean{
        return true
    }

}