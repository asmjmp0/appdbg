package jmp0.app.interceptor.mtd

import jmp0.app.DbgContext
import jmp0.app.mock.MethodManager

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/8
 */
//called from hooked native method
object CallBridge {
    /**
     * if it is system try to use impl/system/class
     * if it is android try to use impl/android/class
     * else pass to user
     * and check native type and implementation
     */
    @JvmStatic
    fun nativeCalled(uuid:String,className:String,funcName:String,signature:String,vararg param:Any?):Any?{
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
    fun methodCalled(uuid: String, className: String, funcName: String, signature: String, vararg param: Any?): Any? {
        val sig = "$className.$funcName$signature"
        val callback = DbgContext.getNativeCallBack(uuid)?:throw Exception("callback no found!!")
        //find implement method and ivoke
        val implMethod = MethodManager.getInstance(uuid).getMethodMap()[sig]
        val res = if (implMethod!=null)  implMethod.invoke(null,uuid,*param)
        else callback.methodCalled(uuid,className, funcName,signature, param)
        return res
    }

}