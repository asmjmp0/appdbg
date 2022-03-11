package jmp0.app.mock.ntv

import jmp0.app.mock.utils.PropertiesReadUtils
import org.apache.log4j.Logger


/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/9
 */
object SystemProperties {
    private val logger = Logger.getLogger(javaClass)

    @JvmStatic
    fun native_get_int(string: String,value: Int):Int {
        val res = PropertiesReadUtils.getProperty(string)?.toInt() ?: value
        logger.debug("native_get_int(string: String,value: Int) called")
        return res
    }
    @JvmStatic
    fun native_get(a: String):String?{
        val res = PropertiesReadUtils.getProperty(a)
        logger.debug("native_get(a: String) called")
        return res
    }

    @JvmStatic
    fun native_get(a: String,b: String):String{
        val res = PropertiesReadUtils.getProperty(a)?:b
        logger.debug("native_get(a: String,b: String) called")
        return res
    }
}