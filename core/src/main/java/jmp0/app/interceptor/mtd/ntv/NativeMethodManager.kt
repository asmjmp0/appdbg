package jmp0.app.interceptor.mtd.ntv

import jmp0.app.interceptor.mtd.ntv.app.*
import java.lang.reflect.Method

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/8
 */
object NativeMethodManager {
    private val hashMap = setCallList()

    private fun setCallList() =
        hashMapOf<String,Method>().apply {
            this["android.util.Log.println_native(IILjava/lang/String;Ljava/lang/String;)I"] =
                Log.javaClass.getDeclaredMethod("println_native",Int::class.java,Int::class.java,String::class.java,String::class.java)

            this["android.os.MessageQueue.nativeInit()J"] =
                MessageQueue.javaClass.getDeclaredMethod("nativeInit")

            this["android.os.Binder.clearCallingIdentity()J"]=
                Binder.javaClass.getDeclaredMethod("clearCallingIdentity")
            this["libcore.io.Linux.getuid()I"] =
                Linux.javaClass.getDeclaredMethod("getuid")
            this["android.os.SystemProperties.native_get_int(Ljava/lang/String;I)I"]=
                SystemProperties.javaClass.getDeclaredMethod("native_get_int",String::class.java,Int::class.java)
            this["android.os.MessageQueue.nativePollOnce(JI)V"]=
                MessageQueue.javaClass.getDeclaredMethod("nativePollOnce",Long::class.java,Int::class.java)
            this["android.os.SystemClock.uptimeMillis()J"]=
                SystemClock.javaClass.getDeclaredMethod("uptimeMillis")
            this["android.os.Binder.flushPendingCommands()V"]=
                Binder.javaClass.getDeclaredMethod("flushPendingCommands")

        }


    fun get(signature:String) = hashMap[signature]
}