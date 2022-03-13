package jmp0.app.mock.ntv

import jmp0.app.mock.NativeHookClass
import org.apache.log4j.Logger
import java.util.Random

@NativeHookClass("android.os.Trace")
object Trace {
    private val logger = Logger.getLogger(Trace::class.java)
    private val random = Random()

    @JvmStatic
    fun nativeGetEnabledTags(uuid: String):Long{
        logger.debug("nativeGetEnabledTags just return")
        return random.nextLong()
    }

    @JvmStatic
    fun nativeTraceBegin(uuid: String,id:Long,content:String){
        logger.debug("nativeTraceBegin just return")
        return
    }

    @JvmStatic
    fun nativeTraceEnd(uuid: String,id:Long){
        logger.debug("nativeTraceEnd just return")
        return
    }


}