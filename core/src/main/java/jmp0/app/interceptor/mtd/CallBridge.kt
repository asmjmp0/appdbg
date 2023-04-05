package jmp0.app.interceptor.mtd

import jmp0.app.DbgContext
import jmp0.app.mock.MethodManager
import jmp0.util.SystemReflectUtils
import jmp0.util.reflection
import java.util.LinkedList

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/8
 */
//called from hooked native method
object CallBridge {

    val nativeCalledName = this::class.java.name + '.' +this::nativeCalled.name
    val methodCalledName = this::class.java.name + '.' +this::methodCalled.name
    /**
     * if it is system try to use impl/system/class
     * if it is android try to use impl/android/class
     * else pass to user
     * and check native type and implementation
     */
    @JvmStatic
    fun nativeCalled(uuid:String,className:String, instance:Any?,funcName:String,signature:String,vararg param:Any?):Any?{
        val sig = "$className.$funcName$signature"
        val callback = DbgContext.getNativeCallBack(uuid)?:throw Exception("callback no found!!")
        //find implement method and ivoke
        val implMethod = MethodManager.getInstance(uuid).getNative(sig)
        val res = if (implMethod!=null)  implMethod.invoke(null,uuid,*param)

        else callback.nativeCalled(uuid,className,funcName,signature,param).apply {
                if (!implemented) throw NativeMethodNotImplementException(sig) }.result
        return res
    }

    @JvmStatic
    fun methodCalled(uuid: String, className: String, instance:Any?, funcName: String, signature: String, vararg param: Any?): Any? {
        val sig = "$className.$funcName$signature"
        val callback = DbgContext.getNativeCallBack(uuid)?:throw Exception("callback no found!!")
        //find implement method and ivoke
        val implMethod = MethodManager.getInstance(uuid).getMethodMap()[sig]
        val res = if (implMethod!=null)  implMethod.invoke(null,uuid,*param)
        else callback.methodCalled(uuid,className, instance,funcName,signature, param)
        return res
    }

    fun methodCallReal(uuid: String, className: String, instance:Any?, funcName: String, signature: String, param:Array<out Any?>): Any? {
        val sig = "$className.$funcName$signature"
        val ae = DbgContext.getAndroidEnvironment(uuid)!!
        val signatureInfo = SystemReflectUtils.getSignatureInfo(sig,uuid)
        return reflection(ae.getClassLoader(),className){
            val method =method(funcName+"Real", *signatureInfo.paramTypes)
            method(instance,*param)
        }
    }

}