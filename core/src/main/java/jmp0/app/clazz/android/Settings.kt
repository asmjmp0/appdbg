package jmp0.app.clazz.android

import android.content.ContentResolver
import org.apache.log4j.Logger

open class Settings {
    open class Secure{
        companion object{
            val logger = Logger.getLogger(javaClass)
            @JvmStatic
            fun getString(contentResolver: ContentResolver?,string: String):String{
                logger.debug("$string need")
                return "Android_11111111"
            }
        }

    }
}