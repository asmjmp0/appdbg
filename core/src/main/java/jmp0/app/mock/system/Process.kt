package jmp0.app.mock.system

import jmp0.app.mock.ClassReplaceTo
import org.apache.log4j.Logger

@ClassReplaceTo("android.os.Process")
class Process {
    companion object{
        private val logger = Logger.getLogger(Process::class.java)

        @JvmStatic
        fun setArgV0(cmd:String){
            logger.warn("setArgV0 $cmd just return")
        }

        @JvmStatic
        fun myUid():Int{
            logger.warn("myUid just return 0")
            return 0
        }

    }
}