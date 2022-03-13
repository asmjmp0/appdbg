package jmp0.app.mock

import javassist.ClassPool
import jmp0.app.DbgContext
import jmp0.app.mock.ntv.*
import jmp0.conf.CommonConf
import jmp0.util.FileUtils
import java.lang.reflect.Method
import jmp0.util.SystemReflectUtils.getMethodWithSignature
import org.objectweb.asm.Opcodes
import java.io.File
import java.util.UUID

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/8
 */
class NativeMethodManager(private val uuid: String) {
    private val hashMap:HashMap<String,Method> = HashMap()
    init {
        setCallList()
    }

    private fun setCallList(){
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
                    val jniSig = method.annotations.find { Annotation-> Annotation is NativeHookReturnType }.run {
                            if (this == null) "${targetClass}.${method.name}${method.signature}".replaceFirst("Ljava/lang/String;","")
                            else{
                                val returnType = (this as NativeHookReturnType).type
                                val realSig = method.signature.replaceFirst("Ljava/lang/String;","").split(')')
                                "${targetClass}.${method.name}${realSig[0]})${returnType}"
                            }
                        }
                    //com.android.internal.os.BinderInternal.getContextObject()Landroid/os/IBinder;
                    val realMethod = Class.forName(ctClass.name).getMethodWithSignature(jniSig,true,uuid)
                    hashMap[jniSig] = realMethod
                }
            }
        }
    }
    fun get(signature:String) = hashMap[signature]
}