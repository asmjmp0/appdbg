package jmp0.app.interceptor.intf

import javassist.*
import jmp0.app.AndroidEnvironment

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/7
 * use to give base func and a interface to  pass chain
 */
abstract class RuntimeClassInterceptorBase(private val androidEnvironment: AndroidEnvironment) {

    protected fun checkNativeFlag(modifiers:Int ) =
        (modifiers and Modifier.NATIVE) == Modifier.NATIVE

    protected fun eraseNativeFlag(modifiers: Int) =
        (modifiers and (Modifier.NATIVE.inv()))


    private fun replaceType(type:String):String {
        var arr = false
        var temp = type
        if (temp.contains("[]")) {
            arr = true
            temp = temp.replace("[]", "")
        }
        return when(temp){
            "byte"->"Byte"
            "short"->"Short"
            "int"->"Integer"
            "long"->"Long"
            "float"->"Float"
            "double"->"Double"
            "boolean"->"Boolean"
            "void"->"Void"
            else->temp
        } + if (arr) "[]" else ""
    }


    protected fun safeGetReturnType(method: CtMethod): String {
        return try {
              replaceType(method.returnType.name)
        }catch (e: NotFoundException){
            //is it array type?
            if (e.message!!.contains("[]")){
                androidEnvironment.loadClass(e.message!!.replace("[]","")).name + "[]"
            }else{
                androidEnvironment.loadClass(e.message!!).name
            }
        }
    }

    /**
     * @param returnValueName the return value name
     * @param returnType which looks like Long Void xxx
     * @return return sentence of the ctMethod
     */
    private data class BaseTypeBody(val method:String,val type: String)
    private fun generateToBaseTypeBody(returnValueName: String,returnType: String):String{
        if (returnType == "Byte[]"){
            //fixup kotlin Byte[] is byte[]
            return "return $returnValueName;"
        }
        val arr = returnType.contains("[]")
        var returnTypeTemp = returnType;
        if(arr){
            returnTypeTemp = returnTypeTemp.replace("[]","")
        }
        val baseToType = when(returnTypeTemp){
                "Byte"->BaseTypeBody(".byteValue()","byte")
                "Short"->BaseTypeBody(".shortValue()","short")
                "Integer"->BaseTypeBody(".intValue()","int")
                "Long"->BaseTypeBody(".longValue()","long")
                "Float"->BaseTypeBody(".floatValue()","float")
                "Double"->BaseTypeBody(".doubleValue()","double")
                "Boolean"->BaseTypeBody(".booleanValue()","boolean")
                else -> return "return $returnValueName;"
        }
        return if (arr){
            """
                    if($returnValueName == null) return null;
                   ${baseToType.type}[] bbXX = new int[$returnValueName.length];
                   for (${baseToType.type} i=0;i<$returnValueName.length;i++){
                          bbXX[i] = ($returnValueName[i])${baseToType.method};
                   }
                    return bbXX;
                """.trimIndent()
        }else{
            "return $returnValueName${baseToType.method};"
        }
    }

    /**
     * @param hookFunc which function need to goto,which looks like "jmp0.app.interceptor.mtd.CallBridge.nativeCalled"
     * @param className origin class name
     * @param funcName origin function name
     * @param signature origin function signature,which looks like (II[J)V
     * @param returnType return type
     * @return the body of ctMethod
     */
    protected fun generateHookBody(hookFunc:String,className: String,funcName:String,
                                   signature:String,returnType:String):String =
        if (returnType == "Void"){
            "{$hookFunc(\"${androidEnvironment.id}\",\"$className\",\"$funcName\",\"$signature\",\$args);}"
            //fixup kotlin Byte[] is byte[]
        }else if (returnType == "Byte[]") {
            "{" + "byte[] ret = (byte[]) $hookFunc(\"${androidEnvironment.id}\",\"$className\",\"$funcName\",\"$signature\",\$args);" +
                    generateToBaseTypeBody("ret",returnType) + "}"
        } else{
            "{" + "$returnType ret = ($returnType) $hookFunc(\"${androidEnvironment.id}\",\"$className\",\"$funcName\",\"$signature\",\$args);" +
                    generateToBaseTypeBody("ret",returnType) + "}"
        }

    abstract fun doChange(ctClass: CtClass):CtClass
}