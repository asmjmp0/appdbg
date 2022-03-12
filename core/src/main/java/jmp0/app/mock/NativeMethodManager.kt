package jmp0.app.mock

import jmp0.app.mock.ntv.*
import java.lang.reflect.Method
import jmp0.util.SystemReflectUtils.getMethodWithSignature
import java.util.UUID

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/8
 */
class NativeMethodManager(private val uuid: String) {
    private val hashMap:HashMap<String,Method> = HashMap<String,Method>()
    init {
        setCallList()
    }

    fun removeFromKey(key:String){
        hashMap.remove(key)
    }

    private fun setCallList(){
        hookLog()
        hookMessageQueue()
        hookBinder()
        hookSystemClock()
        hookSystemProperties()
        hookVMRuntime()
        hookTrace()
        hookBinderInternal()
    }

    private fun hookBinderInternal(){
        val hookList = arrayOf("com.android.internal.os.BinderInternal.getContextObject()Landroid/os/IBinder;")
        hookList.forEach {
            hashMap[it]= BinderInternal.javaClass.getMethodWithSignature(it,true,uuid)
        }
    }

    private fun hookTrace(){
        val hookList = arrayOf("android.os.Trace.nativeGetEnabledTags()J",
        "android.os.Trace.nativeTraceBegin(JLjava/lang/String;)V",
        "android.os.Trace.nativeTraceEnd(J)V")
        hookList.forEach {
            hashMap[it]= Trace.javaClass.getMethodWithSignature(it,true,uuid)
        }
    }

    private fun hookLog(){
        val hookList = arrayOf("android.util.Log.println_native(IILjava/lang/String;Ljava/lang/String;)I",
            "android.util.Log.logger_entry_max_payload_native()I")
        hookList.forEach {
            hashMap[it]= Log.javaClass.getMethodWithSignature(it,true,uuid)
        }
    }

    private fun hookMessageQueue(){
        val hookList = arrayOf("android.os.MessageQueue.nativeInit()J",
            "android.os.MessageQueue.nativePollOnce(JI)V",
            "android.os.MessageQueue.nativeWake(J)V",
            "android.os.MessageQueue.nativeDestroy(J)V")
        hookList.forEach {
            hashMap[it]= MessageQueue.javaClass.getMethodWithSignature(it,true,uuid)
        }
    }

    private fun hookBinder(){
        val hookList = arrayOf("android.os.Binder.clearCallingIdentity()J",
            "android.os.Binder.flushPendingCommands()V",
            "android.os.Binder.init()V")
        hookList.forEach {
            hashMap[it]= Binder.javaClass.getMethodWithSignature(it,true,uuid)
        }
    }

    private fun hookSystemClock(){
        val hookList = arrayOf("android.os.SystemClock.uptimeMillis()J")
        hookList.forEach {
            hashMap[it]= SystemClock.javaClass.getMethodWithSignature(it,true,uuid)
        }
    }

    private fun hookSystemProperties(){
        val hookList = arrayOf("android.os.SystemProperties.native_get_int(Ljava/lang/String;I)I",
            "android.os.SystemProperties.native_get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;",
            "android.os.SystemProperties.native_get(Ljava/lang/String;)Ljava/lang/String;",
            "android.os.SystemProperties.native_add_change_callback()V")
        hookList.forEach {
            hashMap[it]= SystemProperties.javaClass.getMethodWithSignature(it,true,uuid)
        }
    }
    private fun hookVMRuntime(){
        val hookList = arrayOf("dalvik.system.VMRuntime.is64Bit()Z",
            "dalvik.system.VMRuntime.newUnpaddedArray(Ljava/lang/Class;I)Ljava/lang/Object;"
        )
        hookList.forEach {
            hashMap[it]= VMRuntime.javaClass.getMethodWithSignature(it,true,uuid)
        }
    }


    fun get(signature:String) = hashMap[signature]
}