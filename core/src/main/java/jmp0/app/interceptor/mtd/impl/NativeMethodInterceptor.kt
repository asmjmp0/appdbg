package jmp0.app.interceptor.mtd.impl

import javassist.CtClass
import jmp0.app.AndroidEnvironment
import jmp0.app.interceptor.intf.RuntimeClassInterceptorBase
import jmp0.util.SystemReflectUtils.getSignature
import org.apache.log4j.Logger


class NativeMethodInterceptor(private val androidEnvironment: AndroidEnvironment)
    : RuntimeClassInterceptorBase(androidEnvironment) {
    private val logger = Logger.getLogger(javaClass)

    override fun doChange(ctClass: CtClass):CtClass{
        ctClass.declaredMethods.forEach {
            //判断是否为native函数
            if(checkNativeFlag(it.modifiers)){
                //创建到java的bridge
                it.modifiers = eraseNativeFlag(it.modifiers)
                val retTypeName = safeGetReturnType(it)
                val funcName = it.name
                val className = it.declaringClass.name
                val signature = it.signature
                val sig = getSignature(it)
                logger.trace("set native hook to $sig")
                try {
                    val res = generateHookBody("jmp0.app.interceptor.mtd.CallBridge.nativeCalled",
                        className,funcName,signature,retTypeName)
                    it.setBody(res)
                }catch (e:Exception){
                    if ("NotFound" in e.message!!){
                        val a = e.message!!.indexOf(":")
                        val needClassName = e.message!!.substring(a+2,e.message!!.length)
                        androidEnvironment.loadClass(needClassName)
                        it.setBody(generateHookBody("jmp0.app.interceptor.mtd.CallBridge.nativeCalled",
                            className,funcName,signature,retTypeName))
                    }else throw e
                }


            }
        }
        return ctClass
    }
}