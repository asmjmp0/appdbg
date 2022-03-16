package jmp0.app.mock.ntv

import jmp0.app.mock.annotations.NativeHookClass
import org.apache.log4j.Logger
import java.util.Random

@NativeHookClass("android.os.Parcel")
class Parcel {
    companion object{
        private val random = Random()
        private val logger = Logger.getLogger(Parcel::class.java)
        @JvmStatic
        fun nativeCreate(uuid:String):Long{
            return random.nextLong()
        }
    }
}