package jmp0.util

import jmp0.app.DbgContext
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
    private fun getSingleType(signature: String,uuid: String): SingleTypeInfo {
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
            nowClass = if (uuid == "") Class.forName(className)
                else DbgContext.getAndroidEnvironment(uuid)!!.findClass(className)
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
                else->throw java.lang.Exception("${temp[0]} type no defined")
            }
            index++
        }
        return if (arr)  SingleTypeInfo(java.lang.reflect.Array.newInstance(nowClass,0).javaClass,if(index!=signature.length) index else -1)
            else SingleTypeInfo(nowClass,if(index!=signature.length) index else -1)

    }

    /**
     * @param signature looks like IILjava/lang
     */
    private fun getTypeListFromsignature(signature:String,uuid: String):Array<Class<*>>{
        val retList = mutableListOf<Class<*>>()
        var temp:String = signature
        do{
            val (type,index) = getSingleType(temp,uuid)
            retList.add(type)
            if (index == -1) break
            temp = temp.substring(index)
        }while(true)
        return retList.toTypedArray()
    }

     fun getSignatureInfo(signature: String,uuid: String):SignatureInfo{
        val a = signature.split('(',')')
        val cf = a[0]
        val cfindex = cf.lastIndexOf('.')
        val className = cf.substring(IntRange(0,cfindex-1))
        val funcName = cf.substring(cfindex+1)
        val returnType = if(a[2] == "") null else getSingleType(a[2],uuid).type
        val paramTypes = if (a[1] == "") emptyArray() else getTypeListFromsignature(a[1],uuid)
        return SignatureInfo(className, funcName, paramTypes, returnType)
    }

    //exp "android.util.Log.println_native(IILjava/lang/String;Ljava/lang/String;)I"
     fun Class<*>.getMethodWithSignature(signature:String,appendUUID:Boolean,uuid:String):Method{
        val signatureInfo = getSignatureInfo(signature,uuid)
        return if (appendUUID)getDeclaredMethod(signatureInfo.funcName,String::class.java,*(signatureInfo.paramTypes))
        else getDeclaredMethod(signatureInfo.funcName,*(signatureInfo.paramTypes))
    }

    fun Method.invokeEx(ins:Any?,vararg parameter:Any):Any? =
        setAccessibleEx().invoke(ins,*parameter)

    fun Field.getEx(ins: Any) =
        setAccessibleEx().get(ins)

    fun Any.getObjectEx(name:String) =
        javaClass.getDeclaredField(name).getEx(this)

    fun Any.arrayForeach(callback:(idx:Int,obj:Any?)->Unit){
        val size = java.lang.reflect.Array.getLength(this)
        if (size == 0) return
        for (i in 0 until size) callback(i,java.lang.reflect.Array.get(this,i))
    }
    fun Any.arrayGet(idx:Int): Any? {
        val size = java.lang.reflect.Array.getLength(this)
        if (size == 0) return null
        return java.lang.reflect.Array.get(this,idx)
    }

}