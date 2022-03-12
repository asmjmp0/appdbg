package jmp0.app.mock.system

import jmp0.app.mock.MockedBy
import jmp0.app.mock.ReplaceTo
import org.apache.log4j.Logger

@ReplaceTo("android.os.Process")
class Process {
    companion object{
        private val logger = Logger.getLogger(Process::class.java)

        @JvmStatic
        @MockedBy("asmjmp0")
        fun setArgV0(cmd:String){
            logger.warn("setArgV0 $cmd just return")
        }

        @JvmStatic
        @MockedBy("asmjmp0")
        fun myUid():Int{
            logger.warn("myUid just return 0")
            return 0
        }

    }
}