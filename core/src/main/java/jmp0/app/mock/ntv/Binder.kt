package jmp0.app.mock.ntv

import jmp0.app.mock.MockedBy
import org.apache.log4j.Logger


/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/9
 */
object Binder {
    private val logger = Logger.getLogger(Binder::class.java)
    @JvmStatic
    @MockedBy("asmjmp0")
    fun clearCallingIdentity(uuid: String):Long{
        logger.debug("called")
        return 0
    }

    @JvmStatic
    @MockedBy("asmjmp0")
    fun flushPendingCommands(uuid: String){
        logger.debug("called")
    }

    @JvmStatic
    @MockedBy("asmjmp0")
    fun init(uuid: String){
        logger.warn("just returned")
    }
}