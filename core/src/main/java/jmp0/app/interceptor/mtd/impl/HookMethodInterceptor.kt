package jmp0.app.interceptor.mtd.impl

import javassist.CtClass
import jmp0.app.AndroidEnvironment
import jmp0.app.DbgContext
import jmp0.app.interceptor.intf.RuntimeClassInterceptorBase

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/7
 */
class HookMethodInterceptor(private val androidEnvironment: AndroidEnvironment,private val ctClass: CtClass)
    : RuntimeClassInterceptorBase(androidEnvironment,ctClass) {

    override fun doChange(): CtClass {
        for (declaredMethod in ctClass.declaredMethods) {

            //native function can't be hooked,se ClassNativeInterceptor
            if (checkNativeFlag(declaredMethod.modifiers)) continue

            val sig = getSignature(declaredMethod)
            val methodHookList = DbgContext.getMethodHookList(androidEnvironment.id)!!
            methodHookList.find { it.signature == sig }?.also {
                val retTypeName = replaceType(declaredMethod.returnType.name)
                val funcName = declaredMethod.name
                val className = declaredMethod.declaringClass.name
                val signature = declaredMethod.signature
                if (it.replace){
                    if (retTypeName == "Void"){
                        declaredMethod.setBody("{jmp0.app.interceptor.mtd.CallBridge" +
                                ".methodCalled(\"${androidEnvironment.id}\",\"$className\",\"$funcName\",\"$signature\",\$args);}")
                    }else{
                        declaredMethod.setBody("{return ($retTypeName) jmp0.app.interceptor.mtd.CallBridge" +
                                ".methodCalled(\"${androidEnvironment.id}\",\"$className\",\"$funcName\",\"$signature\",\$args);}")
                    }
                }else{
                    //just trace
                    declaredMethod.insertBefore("{jmp0.app.interceptor.mtd.CallBridge" +
                            ".methodCalled(\"${androidEnvironment.id}\",\"$className\",\"$funcName\",\"$signature\",\$args);}")
                }
            }

        }
        return ctClass
    }
}