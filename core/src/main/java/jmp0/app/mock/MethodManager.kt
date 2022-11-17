package jmp0.app.mock

import javassist.ClassPool
import javassist.CtClass
import jmp0.app.DbgContext
import jmp0.app.mock.annotations.HookReturnType
import jmp0.app.mock.annotations.MethodHookClass
import jmp0.app.mock.annotations.NativeHookClass
import jmp0.app.mock.annotations.ParamType
import jmp0.conf.CommonConf
import jmp0.util.FileUtils
import jmp0.util.SystemReflectUtils
import java.lang.reflect.Method
import jmp0.util.SystemReflectUtils.getMethodWithSignature
import jmp0.util.reflection
import java.io.File

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/8
 */
class MethodManager(private val mUuid: String) {
    private val nativeHashMap:HashMap<String,Method> = HashMap()
    private val methodHashMap:HashMap<String,Method> = HashMap()
    companion object{
        private val insMap:HashMap<String,MethodManager> = HashMap()

        fun getInstance(uuid: String): MethodManager {
            return insMap[uuid]?:uuid.run {
                val ins = MethodManager(this)
                insMap[uuid] = ins
                ins
            }
        }

        fun removeInstance(uuid: String) =
            insMap.remove(uuid)
    }
    init {
        setNativeCallList()
        setMethodCallList()
    }

    private fun setCallListInternal(targetClass:String,ctClass: CtClass,map:HashMap<String,Method>){
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
                val realMethod = Class.forName(ctClass.name).getMethodWithSignature(jniSig,true,mUuid)
                map[jniReal] = realMethod
            }
        }

    }

    private fun setNativeCallList(){
        SystemReflectUtils.getAllClassWithAnnotation(CommonConf.Mock.mockNativeClassPackageName,NativeHookClass::class.java){ fullClassName,isInner->
            val ctClass = ClassPool.getDefault().getCtClass(fullClassName)
            val targetClass = if(isInner) fullClassName else (ctClass.annotations.find { it is NativeHookClass } as NativeHookClass).targetClass
            setCallListInternal(targetClass,ctClass,nativeHashMap)
        }
    }

    private fun setMethodCallList(){
        SystemReflectUtils.getAllClassWithAnnotation(CommonConf.Mock.mockMethodClassPackageName,MethodHookClass::class.java){ fullClassName,isInner->
            val ctClass = ClassPool.getDefault().getCtClass(fullClassName)
            val targetClass = if(isInner) fullClassName else (ctClass.annotations.find { it is MethodHookClass } as MethodHookClass).targetClass
            setCallListInternal(targetClass,ctClass,methodHashMap)
        }
    }
    fun getNative(signature:String) = nativeHashMap[signature]
    fun getMethodMap() = methodHashMap
}