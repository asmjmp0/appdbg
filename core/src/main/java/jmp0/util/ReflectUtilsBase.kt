package jmp0.util

import java.lang.reflect.Field
import java.lang.reflect.Method

abstract class ReflectUtilsBase {

    private fun Method.setAccessibleEx() = apply {
        isAccessible = true
    }

    private fun Field.setAccessibleEx() = apply {
        isAccessible = true
    }

    private data class SignatureInfo(val className:String,val funcName:String,val paramTypes:Array<Class<*>>,val returnType:Class<*>)

    private fun getSignatureInfo(signature: String):SignatureInfo{
        TODO("not implement yet")
    }

    //exp "android.util.Log.println_native(IILjava/lang/String;Ljava/lang/String;)I"
    protected fun Class<*>.getMethodWithSignature(signature:String):Method{
        val signatureInfo = getSignatureInfo(signature)
        return getDeclaredMethod(signatureInfo.funcName,*(signatureInfo.paramTypes))
    }

    fun Method.invokeEx(ins:Any,vararg parameter:Any):Any? =
        setAccessibleEx().invoke(ins,*parameter)

    fun Field.getEx(ins: Any) =
        setAccessibleEx().get(ins)
}