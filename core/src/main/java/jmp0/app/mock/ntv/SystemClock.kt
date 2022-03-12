package jmp0.app.mock.ntv

import jmp0.app.mock.MockedBy
import org.apache.log4j.Logger

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/9
 */
object SystemClock {
    private val logger = Logger.getLogger(javaClass)
    @JvmStatic
    @MockedBy("asmjmp0")
    fun uptimeMillis(uuid: String):Long{
        logger.debug("called")
        return System.currentTimeMillis() and 0x1fffff
    }
}