package jmp0.app.mock

import jmp0.app.mock.ntv.*
import java.lang.reflect.Method
import jmp0.util.SystemReflectUtils.getMethodWithSignature

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/8
 */
object NativeMethodManager {
    private val hashMap = setCallList()

    fun removeFromKey(key:String){
        hashMap.remove(key)
    }

    private fun setCallList() =
        hashMapOf<String,Method>().apply {
            hookLog(this)
            hookMessageQueue(this)
            hookBinder(this)
            hookSystemClock(this)
            hookSystemProperties(this)
            hookVMRuntime(this)
        }

    private fun hookLog(mHashMap: HashMap<String,Method>){
        val hookList = arrayOf("android.util.Log.println_native(IILjava/lang/String;Ljava/lang/String;)I","android.util.Log.logger_entry_max_payload_native()I")
        hookList.forEach {
            mHashMap[it]= Log.javaClass.getMethodWithSignature(it)
        }
    }

    private fun hookMessageQueue(mHashMap: HashMap<String,Method>){
        val hookList = arrayOf("android.os.MessageQueue.nativeInit()J","android.os.MessageQueue.nativePollOnce(JI)V","android.os.MessageQueue.nativeWake(J)V")
        hookList.forEach {
            mHashMap[it]= MessageQueue.javaClass.getMethodWithSignature(it)
        }
    }

    private fun hookBinder(mHashMap: HashMap<String,Method>){
        val hookList = arrayOf("android.os.Binder.clearCallingIdentity()J","android.os.Binder.flushPendingCommands()V")
        hookList.forEach {
            mHashMap[it]= Binder.javaClass.getMethodWithSignature(it)
        }
    }

    private fun hookSystemClock(mHashMap: HashMap<String,Method>){
        val hookList = arrayOf("android.os.SystemClock.uptimeMillis()J")
        hookList.forEach {
            mHashMap[it]= SystemClock.javaClass.getMethodWithSignature(it)
        }
    }

    private fun hookSystemProperties(mHashMap: HashMap<String, Method>){
        val hookList = arrayOf("android.os.SystemProperties.native_get_int(Ljava/lang/String;I)I",
            "android.os.SystemProperties.native_get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;",
            "android.os.SystemProperties.native_get(Ljava/lang/String;)Ljava/lang/String;")
        hookList.forEach {
            mHashMap[it]= SystemProperties.javaClass.getMethodWithSignature(it)
        }
    }
    private fun hookVMRuntime(mHashMap: HashMap<String, Method>){
        val hookList = arrayOf("dalvik.system.VMRuntime.is64Bit()Z",
        )
        hookList.forEach {
            mHashMap[it]= VMRuntime.javaClass.getMethodWithSignature(it)
        }
    }


    fun get(signature:String) = hashMap[signature]
}