package jmp0.app.interceptor.mtd.ntv.app

import org.apache.log4j.Logger


/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/9
 */
object MessageQueue {
    private val logger = Logger.getLogger(javaClass)
    @JvmStatic
    fun nativeInit():Long{
        return 0L
    }

    @JvmStatic
    fun nativePollOnce(long: Long,int: Int){
        logger.debug("called")
    }
}