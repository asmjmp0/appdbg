package jmp0.app.interceptor.mtd.ntv.app

import org.apache.log4j.Logger
import java.util.concurrent.CountDownLatch


/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/9
 */
object MessageQueue {
    private val logger = Logger.getLogger(javaClass)
    private lateinit var latch:CountDownLatch
    @JvmStatic
    fun nativeInit():Long{
        return 0L
    }

    @JvmStatic
    fun nativePollOnce(id: Long,int: Int){
        if (int == -1){
            latch = CountDownLatch(1)
            latch.await()
        }
    }

    @JvmStatic
    fun nativeWake(id:Long){
        latch.countDown()
    }
}