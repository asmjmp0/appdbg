package jmp0.app.mock.system

import android.os.UserHandle
import jmp0.app.DbgContext
import jmp0.app.mock.ClassReplaceTo
import jmp0.app.mock.HookReturnType
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

        @JvmStatic
        @HookReturnType("android.os.Process.myUserHandle()Landroid/os/UserHandle;")
        fun myUserHandle():Any{
            val uuid = Class.forName("android.os.Process",false,Thread.currentThread().contextClassLoader).getDeclaredField("xxUuid").get(null) as String
            val androidEnvironment = DbgContext.getAndroidEnvironment(uuid)
            return androidEnvironment!!.findClass("android.os.UserHandle")
                .getDeclaredConstructor(Int::class.java)
                .newInstance(0)
        }

    }
}