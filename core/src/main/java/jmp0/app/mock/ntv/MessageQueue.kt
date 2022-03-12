package jmp0.app.mock.ntv

import jmp0.app.mock.MockedBy
import org.apache.log4j.Logger
import java.util.Random
import java.util.concurrent.CountDownLatch


/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/9
 */
// TODO: 2022/3/11 multi looper support
object MessageQueue {
    private val logger = Logger.getLogger(javaClass)
    private val looperMap = HashMap<Long,CountDownLatch?>()
    @JvmStatic
    @MockedBy("asmjmp0")
    fun nativeInit(uuid: String):Long{
        val id = Random().nextLong()
        looperMap[id] = null
        return id
    }

    @JvmStatic
    @MockedBy("asmjmp0")
    fun nativePollOnce(uuid: String,id: Long,int: Int){
        if (int == -1){
            looperMap[id]  = CountDownLatch(1)
            looperMap[id]!!.await()
        }
    }

    @JvmStatic
    @MockedBy("asmjmp0")
    fun nativeWake(uuid: String,id:Long){
        looperMap[id]!!.countDown()
    }

    @JvmStatic
    @MockedBy("asmjmp0")
    fun nativeDestroy(uuid: String,id:Long){
        looperMap.remove(id)
    }
}