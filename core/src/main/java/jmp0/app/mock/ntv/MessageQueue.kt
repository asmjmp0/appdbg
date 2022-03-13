package jmp0.app.mock.ntv

import jmp0.app.mock.NativeHookClass
import org.apache.log4j.Logger
import java.util.Random
import java.util.concurrent.CountDownLatch


/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/9
 */
// TODO: 2022/3/11 multi looper support
@NativeHookClass("android.os.MessageQueue")
object MessageQueue {
    private val logger = Logger.getLogger(javaClass)
    private val looperMap = HashMap<Long,CountDownLatch?>()

    @JvmStatic
    fun nativeInit(uuid: String):Long{
        val id = Random().nextLong()
        looperMap[id] = null
        return id
    }

    @JvmStatic
    fun nativePollOnce(uuid: String,id: Long,int: Int){
        if (int == -1){
            looperMap[id]  = CountDownLatch(1)
            looperMap[id]!!.await()
        }
    }

    @JvmStatic
    fun nativeWake(uuid: String,id:Long){
        println(Thread.currentThread().contextClassLoader)
        looperMap[id]!!.countDown()
    }

    @JvmStatic
    fun nativeDestroy(uuid: String,id:Long){
        looperMap.remove(id)
    }
}