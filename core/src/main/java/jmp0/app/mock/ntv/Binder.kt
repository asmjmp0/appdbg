package jmp0.app.mock.ntv

import jmp0.app.mock.annotations.NativeHookClass
import org.apache.log4j.Logger


/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/9
 */
@NativeHookClass("android.os.Binder")
object Binder {
    private val logger = Logger.getLogger(Binder::class.java)

    @JvmStatic
    fun clearCallingIdentity(uuid: String):Long{
        logger.debug("called")
        return 0
    }

    @JvmStatic
    fun flushPendingCommands(uuid: String){
        logger.debug("called")
    }

    @JvmStatic
    fun init(uuid: String){
        logger.warn("just returned")
    }

    @JvmStatic
    fun setThreadStrictModePolicy(uuid: String,a:Int){
        logger.debug("setThreadStrictModePolicy")
    }
}