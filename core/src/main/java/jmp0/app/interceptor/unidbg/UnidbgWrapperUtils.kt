package jmp0.app.interceptor.unidbg

import com.github.unidbg.linux.android.dvm.*
import jmp0.util.ReflectUtilsBase
import jmp0.util.SystemReflectUtils

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

    fun toUnidbgObject(vm:VM,obj:Any?):DvmObjectWrapper{
        if (obj == null) return DvmObjectWrapper(vm.resolveClass("java/lang/String"),null)
        val clazzName = obj.javaClass.name.replace(".","/")
        return DvmObjectWrapper(vm.resolveClass(clazzName),obj)
    }

    fun wrapperToUnidbgParams(vm: VM,param: Array<out Any?>): Array<out Any> {
        val retArr = ArrayList<DvmObject<*>?>()
        if (param.isEmpty()) return retArr.toArray()
        param.forEach {
            retArr.add(toUnidbgObject(vm,it))
        }
        return retArr.toArray()
    }
}