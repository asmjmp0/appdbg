package jmp0.util

import sun.jvm.hotspot.utilities.TwoOopHashtable
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

    data class SignatureInfo(val className:String,val funcName:String,val paramTypes:Array<Class<*>>,val returnType:Class<*>?)
    private data class SingleTypeInfo(val type:Class<*>,val index:Int)

    /**
     * @param signature looks like I J
     */
    private fun getSingleType(signature: String): SingleTypeInfo {
        var arr = false
        val nowClass:Class<*>
        var temp = signature
        var index = 0
        //is array type
        if(temp.startsWith("[")){
            arr = true
            temp = temp.slice(IntRange(1,temp.length-1))
            index++
        }

        //is class type
        if (temp.startsWith("L")){
            val cIndex = temp.indexOf(';')
            temp = temp.slice(IntRange(1,cIndex-1))
            val className = temp.replace('/','.')
            index += cIndex+1
            nowClass = Class.forName(className)
        }else{
            nowClass = when(temp[0]){
                'Z'-> Boolean::class.java
                'B'-> Byte::class.java
                'C'-> Char::class.java
                'S'-> Short::class.java
                'I'-> Int::class.java
                'J'-> Long::class.java
                'F'-> Float::class.java
                'D'-> Double::class.java
                'V'-> Void::class.java
                else->throw java.lang.Exception("type no defined")
            }
            index++
        }
        return if (arr)  SingleTypeInfo(java.lang.reflect.Array.newInstance(nowClass,0).javaClass,if(index!=signature.length) index else -1)
            else SingleTypeInfo(nowClass,if(index!=signature.length) index else -1)

    }

    /**
     * @param signature looks like IILjava/lang
     */
    private fun getTypeListFromsignature(signature:String):Array<Class<*>>{
        val retList = mutableListOf<Class<*>>()
        var temp:String = signature
        do{
            val (type,index) = getSingleType(temp)
            retList.add(type)
            if (index == -1) break
            temp = temp.substring(index)
        }while(true)
        return retList.toTypedArray()
    }

     fun getSignatureInfo(signature: String):SignatureInfo{
        val a = signature.split('(',')')
        val cf = a[0]
        val cfindex = cf.lastIndexOf('.')
        val className = cf.substring(IntRange(0,cfindex-1))
        val funcName = cf.substring(cfindex+1)
        val returnType = if(a[2] == "") null else getSingleType(a[2]).type
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