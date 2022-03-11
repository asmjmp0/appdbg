package jmp0.app.mock.ntv

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/8
 */
object Log {

    @JvmStatic
    fun println_native(a: Int,b:Int,tag:String,content:String):Int{
        println("android log [$tag] $content")
        return 1
    }

    @JvmStatic
    fun logger_entry_max_payload_native():Int{
        return 1024
    }
}