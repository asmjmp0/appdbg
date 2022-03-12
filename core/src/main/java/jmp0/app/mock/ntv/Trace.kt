package jmp0.app.mock.ntv

import jmp0.app.mock.MockedBy
import java.util.Random


object Trace {
    private val tagMap = HashMap<Long,String>()
    private val random = Random()

    @JvmStatic
    @MockedBy("asmjmp0")
    fun nativeGetEnabledTags(uuid: String):Long{
        val tag = random.nextLong()
        tagMap[tag] = uuid
        return tag
    }

    @JvmStatic
    @MockedBy("asmjmp0")
    fun nativeTraceBegin(uuid: String,id:Long,content:String){
        return
    }

    @JvmStatic
    @MockedBy("asmjmp0")
    fun nativeTraceEnd(uuid: String,id:Long){
        return
    }


}