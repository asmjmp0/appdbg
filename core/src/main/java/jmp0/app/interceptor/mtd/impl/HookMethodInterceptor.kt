package jmp0.app.interceptor.mtd.impl

import javassist.CtClass
import javassist.CtMethod
import jmp0.app.AndroidEnvironment
import jmp0.app.DbgContext
import jmp0.app.interceptor.intf.RuntimeClassInterceptorBase
import jmp0.app.interceptor.mtd.CallBridge
import jmp0.util.SystemReflectUtils.getSignature

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/7
 */
class HookMethodInterceptor(private val androidEnvironment: AndroidEnvironment)
    : RuntimeClassInterceptorBase(androidEnvironment) {

    override fun doChange(ctClass: CtClass): CtClass {
        for (declaredMethod in ctClass.declaredMethods) {

            //native function can't be hooked,se ClassNativeInterceptor
            if (checkNativeFlag(declaredMethod.modifiers)) continue
            val isStatic = checkStaticFlag(declaredMethod.modifiers)
            val sig = getSignature(declaredMethod)
            val methodHookList = DbgContext.getMethodHookList(androidEnvironment.id)!!
            methodHookList.find { it.signature == sig }?.also {
                val retTypeName = safeGetReturnType(declaredMethod)
                val funcName = declaredMethod.name
                val className = declaredMethod.declaringClass.name
                val signature = declaredMethod.signature
                ctClass.addMethod(CtMethod(declaredMethod,ctClass,null).apply {
                    name = declaredMethod.name + "Real"
                })
                try {
                    val res = generateHookBody(CallBridge.methodCalledName,
                        className,funcName,signature,retTypeName,isStatic)
                    declaredMethod.setBody(res)
                }catch (e:Exception){
                    if ("NotFound" in e.message!!){
                        val a = e.message!!.indexOf(":")
                        val needClassName = e.message!!.substring(a+2,e.message!!.length)
                        androidEnvironment.loadClass(needClassName)
                        declaredMethod.setBody(generateHookBody(CallBridge.methodCalledName,
                            className,funcName,signature,retTypeName,isStatic))
                    }else throw e

                }
            }

        }
        return ctClass
    }
}