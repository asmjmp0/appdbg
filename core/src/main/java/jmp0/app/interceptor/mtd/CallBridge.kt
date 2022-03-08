package jmp0.app.interceptor.mtd

import jmp0.app.DbgContext
import jmp0.app.interceptor.mtd.ntv.NativeMethodManager

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
        val implMethod = NativeMethodManager.get(sig)
        return if (implMethod!=null) implMethod.invoke(null,*param)

        else callback.nativeCalled(className,funcName,param).apply {
                if (!implemented) throw NativeMethodNotImplementException(sig) }.result
    }

}