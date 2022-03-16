package jmp0.app.mock

import javassist.ClassPool
import jmp0.app.mock.annotations.HookReturnType
import jmp0.app.mock.annotations.MethodHookClass
import jmp0.app.mock.annotations.NativeHookClass
import jmp0.app.mock.annotations.ParamType
import jmp0.conf.CommonConf
import jmp0.util.FileUtils
import java.lang.reflect.Method
import jmp0.util.SystemReflectUtils.getMethodWithSignature
import java.io.File

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/8
 */
class MethodManager(private val uuid: String) {
    private val nativeHashMap:HashMap<String,Method> = HashMap()
    private val methodHashMap:HashMap<String,Method> = HashMap()
    init {
        setNativeCallList()
        setMethodCallList()
    }

    private fun setNativeCallList(){
        FileUtils.listFileRecursive(
            File(CommonConf.Mock.mockNativeClass),
            CommonConf.Mock.mockNativeClassPackageName){ pName, fileName->
            val fullClassName = pName +'.'+ fileName.split('.')[0]
            val ctClass = ClassPool.getDefault().getCtClass(fullClassName)
            val targetClass = ctClass.annotations.find { it is NativeHookClass }.run {
                if(this != null) (this as NativeHookClass).targetClass
                else throw Exception("${ctClass.name} not set targetClass by ${NativeHookClass::class.java.name} annotations")
            }
            ctClass.declaredMethods.forEach { method->
                if(method.annotations.find { annotation-> annotation is JvmStatic }!=null){
                    val jniSig = method.annotations.find { Annotation-> Annotation is HookReturnType }.run {
                            if (this == null) "${targetClass}.${method.name}${method.signature}".replaceFirst("Ljava/lang/String;","")
                            else{
                                val returnType = (this as HookReturnType).type
                                val realSig = method.signature.replaceFirst("Ljava/lang/String;","").split(')')
                                "${targetClass}.${method.name}${realSig[0]})${returnType}"
                            }
                        }
                    var jniReal = jniSig
                    method.parameterAnnotations.forEach {
                        it.forEach {
                            if(it is ParamType){
                                val type = it.type
                                val before = it.before
                                //android.content.res.AssetManager.loadResourceValue(ISLjava/lang/Object;Z)I
                                jniReal = jniSig.replaceFirst("${before}Ljava/lang/Object;",before+type)
                            }
                        }
                    }
                    val realMethod = Class.forName(ctClass.name).getMethodWithSignature(jniSig,true,uuid)
                    nativeHashMap[jniReal] = realMethod
                }
            }
        }
    }

    private fun setMethodCallList(){
        FileUtils.listFileRecursive(
            File(CommonConf.Mock.mockMethodClass),
            CommonConf.Mock.mockMethodClassPackageName){ pName, fileName->
            val fullClassName = pName +'.'+ fileName.split('.')[0]
            val ctClass = ClassPool.getDefault().getCtClass(fullClassName)
            val targetClass = ctClass.annotations.find { it is MethodHookClass }.run {
                if(this != null) (this as MethodHookClass).targetClass
                else throw Exception("${ctClass.name} not set targetClass by ${MethodHookClass::class.java.name} annotations")
            }
            ctClass.declaredMethods.forEach { method->
                if(method.annotations.find { annotation-> annotation is JvmStatic }!=null){
                    val jniSig = method.annotations.find { Annotation-> Annotation is HookReturnType }.run {
                        if (this == null) "${targetClass}.${method.name}${method.signature}".replaceFirst("Ljava/lang/String;","")
                        else{
                            val returnType = (this as HookReturnType).type
                            val realSig = method.signature.replaceFirst("Ljava/lang/String;","").split(')')
                            "${targetClass}.${method.name}${realSig[0]})${returnType}"
                        }
                    }
                    var jniReal = jniSig
                    method.parameterAnnotations.forEach {
                        it.forEach {
                            if(it is ParamType){
                                val type = it.type
                                val before = it.before
                                //android.content.res.AssetManager.loadResourceValue(ISLjava/lang/Object;Z)I
                                jniReal = jniSig.replaceFirst("${before}Ljava/lang/Object;",before+type)
                            }
                        }
                    }
                    val realMethod = Class.forName(ctClass.name).getMethodWithSignature(jniSig,true,uuid)
                    methodHashMap[jniReal] = realMethod
                }
            }
        }
    }
    fun getNative(signature:String) = nativeHashMap[signature]
    fun getMethodMap() = methodHashMap
}