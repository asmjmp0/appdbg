package jmp0.app.mock.ntv

import jmp0.app.mock.NativeHookClass
import java.util.UUID

@NativeHookClass("libcore.icu.ICU")
class ICU {
    companion object{
        @JvmStatic
        fun addLikelySubtags(uuid: String,a:String):String{
            return "en_US"
        }
    }
}