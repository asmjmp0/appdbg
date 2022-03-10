package jmp0.app.interceptor.mtd.ntv

import jmp0.app.interceptor.mtd.ntv.app.*
import java.lang.reflect.Method
import jmp0.util.SystemReflectUtils.getMethodWithSignature

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/8
 */
object NativeMethodManager {
    private val hashMap = setCallList()

    private fun setCallList() =
        hashMapOf<String,Method>().apply {
            hookLog(this)
            hookMessageQueue(this)
            hookBinder(this)
            hookSystemClock(this)
            // TODO: 2022/3/10 transform to jmp0.app.clazz.system.android load before
            this["libcore.io.Linux.getuid()I"] =
                Linux.javaClass.getDeclaredMethod("getuid")
            // TODO: 2022/3/10 transform to jmp0.app.clazz.system.android load before
            this["android.os.SystemProperties.native_get_int(Ljava/lang/String;I)I"]=
                SystemProperties.javaClass.getDeclaredMethod("native_get_int",String::class.java,Int::class.java)
        }

    private fun hookLog(mHashMap: HashMap<String,Method>){
        val hookList = arrayOf("android.util.Log.println_native(IILjava/lang/String;Ljava/lang/String;)I","android.util.Log.logger_entry_max_payload_native()I")
        hookList.forEach {
            mHashMap[it]=Log.javaClass.getMethodWithSignature(it)
        }
    }

    private fun hookMessageQueue(mHashMap: HashMap<String,Method>){
        val hookList = arrayOf("android.os.MessageQueue.nativeInit()J","android.os.MessageQueue.nativePollOnce(JI)V","android.os.MessageQueue.nativeWake(J)V")
        hookList.forEach {
            mHashMap[it]=MessageQueue.javaClass.getMethodWithSignature(it)
        }
    }

    private fun hookBinder(mHashMap: HashMap<String,Method>){
        val hookList = arrayOf("android.os.Binder.clearCallingIdentity()J","android.os.Binder.flushPendingCommands()V")
        hookList.forEach {
            mHashMap[it]=Binder.javaClass.getMethodWithSignature(it)
        }
    }

    private fun hookSystemClock(mHashMap: HashMap<String,Method>){
        val hookList = arrayOf("android.os.SystemClock.uptimeMillis()J")
        hookList.forEach {
            mHashMap[it]=SystemClock.javaClass.getMethodWithSignature(it)
        }
    }


    fun get(signature:String) = hashMap[signature]
}