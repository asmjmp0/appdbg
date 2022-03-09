package jmp0.util

import java.lang.reflect.Field
import java.lang.reflect.Method
import java.time.temporal.Temporal

abstract class ReflectUtilsBase {

    private fun Method.setAccessibleEx() = apply {
        isAccessible = true
    }

    private fun Field.setAccessibleEx() = apply {
        isAccessible = true
    }

    private data class SignatureInfo(val className:String,val funcName:String,val paramTypes:Array<Class<*>>,val returnType:Class<*>?)


    /**
     * @param signature looks like I J
     */
    private fun getSingleType(signature: String): Class<*>? {
        var arr:Boolean = false
        var nowClass:Class<*> = Any::class.java
        var temp = signature
        //is array type
        if(temp.startsWith("[")){
            arr = true
            temp = temp.slice(IntRange(1,temp.length-1))
        }

        //is class type
        if (temp.startsWith("L")){
            temp = temp.slice(IntRange(2,temp.length-2))
            nowClass = Class.forName(temp.replace('/','.'))
        }else{
            nowClass = when(temp){
                "Z"-> Boolean::class.java
                "B"-> Byte::class.java
                "C"-> Char::class.java
                "S"-> Short::class.java
                "I"-> Int::class.java
                "J"-> Long::class.java
                "F"-> Float::class.java
                "D"-> Double::class.java
                "V"-> Void::class.java
                else->throw java.lang.Exception("type no defined")
            }
        }
        return if (arr)  java.lang.reflect.Array.newInstance(nowClass,0).javaClass
            else nowClass

    }

    /**
     * @param signature looks like IILjava/lang
     */
    private fun getTypeListFromsignature(signature:String):Array<Class<*>>{
        var temp = signature
        // TODO: 2022/3/10 实现这个功能
        return emptyArray()
    }

    private fun getSignatureInfo(signature: String):SignatureInfo{
        val a = signature.split('(',')')
        val cf = a[0]
        val cfindex = cf.lastIndexOf('.')
        val className = cf.substring(IntRange(0,cfindex-1))
        val funcName = cf.substring(cfindex+1)
        val returnType = if(a[2] == "") null else getSingleType(a[2])
        val paramTypes = if (a[1] == "") emptyArray() else getTypeListFromsignature(a[1])
        return SignatureInfo(className, funcName, paramTypes, returnType)
    }

    //exp "android.util.Log.println_native(IILjava/lang/String;Ljava/lang/String;)I"
     fun Class<*>.getMethodWithSignature(signature:String):Method{
        val signatureInfo = getSignatureInfo(signature)
        return getDeclaredMethod(signatureInfo.funcName,*(signatureInfo.paramTypes))
    }

    fun Method.invokeEx(ins:Any,vararg parameter:Any):Any? =
        setAccessibleEx().invoke(ins,*parameter)

    fun Field.getEx(ins: Any) =
        setAccessibleEx().get(ins)
}