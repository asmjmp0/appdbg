package jmp0.app.interceptor.mtd.ntv.app

import jmp0.app.interceptor.mtd.NativeMethodNotImplementException
import org.apache.log4j.Logger


/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/9
 */
object SystemProperties {
    private val logger = Logger.getLogger(javaClass)

    // TODO: 2022/3/11 use properties from mock-android
    @JvmStatic
    fun native_get_int(string: String,int: Int):Int{
        logger.debug("called")
        return 0
    }

    @JvmStatic
    fun native_get(a: String):String?{
        if (a == "ro.product.cpu.abilist"){
            return "arm64-v8a,armeabi-v7a,armeabi"
        }
        return ""
    }

    @JvmStatic
    fun native_get(a: String,b: String):String{
        logger.debug("called")
        if (a == "ro.build.id"){
            return "QKQ1.190825.002"
        }
        if(a == "ro.build.display.id"){
            return "QKQ1.190825.002 test-keys"
        }
        if (a == "ro.product.name"){
            return "cepheus"
        }
        return b
//        throw NativeMethodNotImplementException("SystemProperties.native_get")
    }
}