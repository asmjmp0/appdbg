package jmp0.app.interceptor.unidbg

import com.github.unidbg.linux.android.dvm.*
import com.github.unidbg.linux.android.dvm.array.ArrayObject
import com.github.unidbg.linux.android.dvm.wrapper.DvmBoolean
import com.github.unidbg.linux.android.dvm.wrapper.DvmInteger
import com.github.unidbg.linux.android.dvm.wrapper.DvmLong
import jmp0.util.ReflectUtilsBase
import jmp0.util.SystemReflectUtils
import java.util.LinkedList

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/4/22
 */
object UnidbgWrapperUtils {
    fun toOriginalObject(vaList: VaList, signatureInfo: ReflectUtilsBase.SignatureInfo):Array<Any?>{
        if (signatureInfo.paramTypes.isEmpty()) return emptyArray()
        val retArr = ArrayList<Any?>()
        val size = signatureInfo.paramTypes.size
        for (i in 0 until size){
            when(signatureInfo.paramTypes[i].name){
                "int"-> {
                    val intObj = vaList.getIntArg(i)
                    retArr.add(intObj)
                }
                "double"-> {
                    val doubleObj = vaList.getDoubleArg(i)
                    retArr.add(doubleObj)
                }
                "float"-> {
                    val floatObj = vaList.getFloatArg(i)
                    retArr.add(floatObj)
                }
                "long"-> {
                    val longObj = vaList.getLongArg(i)
                    retArr.add(longObj)
                }
                else -> {
                    val dvmObj = vaList.getObjectArg<DvmObject<*>>(i)
                    retArr.add(dvmObj.value)
                }
            }

        }
        return retArr.toArray()
    }

    fun toUnidbgObject(vm:VM,obj:Any?,className:String? = null):DvmObject<out Any>{
        if (obj == null) return DvmObjectWrapper(vm.resolveClass("java/lang/String"),null)
        //fixme wrapper fix
        when(obj){
            is String -> return StringObject(vm,obj)

            is Long -> return DvmLong.valueOf(vm,obj)
            is Boolean -> return DvmBoolean.valueOf(vm,obj)
            is Int -> return DvmInteger.valueOf(vm,obj)

            is ByteArray -> return com.github.unidbg.linux.android.dvm.array.ByteArray(vm,obj)
            is DoubleArray -> return com.github.unidbg.linux.android.dvm.array.DoubleArray(vm,obj)
            is FloatArray -> return com.github.unidbg.linux.android.dvm.array.FloatArray(vm,obj)
            is IntArray -> return com.github.unidbg.linux.android.dvm.array.IntArray(vm,obj)
            is ShortArray -> return com.github.unidbg.linux.android.dvm.array.ShortArray(vm,obj)

            is Array<*> -> {
                val objectList = LinkedList<DvmObject<*>>()
                obj.forEach { objectList.add(toUnidbgObject(vm,it)) }
                return ArrayObject(*objectList.toTypedArray())
            }
        }
        val clazzName = className?:obj.javaClass.name.replace(".","/")
        return DvmObjectWrapper(vm.resolveClass(clazzName),obj)
    }

    fun wrapperToUnidbgParams(vm: VM,param: Array<out Any?>): Array<out Any> {
        val retArr = ArrayList<Any>()
        if (param.isEmpty()) return retArr.toArray()
        param.forEach {
            retArr.add(toUnidbgObject(vm,it))
        }
        return retArr.toArray()
    }
}