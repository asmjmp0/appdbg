package jmp0.app.interceptor.mtd.ntv.app

import org.apache.log4j.Logger


/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/9
 */
object SystemProperties {
    private val logger = Logger.getLogger(javaClass)
    @JvmStatic
    fun native_get_int(string: String,int: Int):Int{
        return 0
    }
}