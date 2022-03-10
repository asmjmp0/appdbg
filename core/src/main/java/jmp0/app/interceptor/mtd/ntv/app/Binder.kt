package jmp0.app.interceptor.mtd.ntv.app

import org.apache.log4j.Logger


/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/9
 */
object Binder {
    private val logger = Logger.getLogger(this.javaClass)
    @JvmStatic
    fun clearCallingIdentity():Long{
        logger.debug("called")
        return 0
    }

    @JvmStatic
    fun flushPendingCommands(){
        logger.debug("called")
    }
}