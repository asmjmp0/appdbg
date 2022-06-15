package jmp0.app.mock.ntv

import jmp0.app.mock.annotations.HookReturnType
import jmp0.app.mock.annotations.NativeHookClass
import org.apache.log4j.Logger

@NativeHookClass("android.os.Process")
class Process {
    companion object{
        private val logger = Logger.getLogger(Process::class.java)

        @JvmStatic
        fun setArgV0(uuid:String,cmd:String){
            logger.warn("setArgV0 $cmd just return")
        }

        @JvmStatic
        fun myUid(uuid:String):Int{
            logger.warn("myUid just return 0")
            return 0
        }

        @JvmStatic
        @HookReturnType("Landroid/os/UserHandle;")
        fun myUserHandle(uuid:String):Any?{
            return null;
        }
        @JvmStatic
        fun setThreadPriority(uuid: String,i:Int){
            return
        }

    }
}