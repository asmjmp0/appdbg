package jmp0.app.interceptor.mtd.impl

import javassist.CtClass
import jmp0.app.AndroidEnvironment
import jmp0.app.interceptor.intf.RuntimeClassInterceptorBase
import org.apache.log4j.Logger


class ClassNativeInterceptor(private val androidEnvironment: AndroidEnvironment,private val ctClass: CtClass)
    : RuntimeClassInterceptorBase(androidEnvironment,ctClass) {
    private val logger = Logger.getLogger(javaClass)

    override fun doChange():CtClass{
        ctClass.declaredMethods.forEach {
            //判断是否为native函数
            if(checkNativeFlag(it.modifiers)){
                //创建到java的bridge
                it.modifiers = eraseNativeFlag(it.modifiers)
                val retTypeName = replaceType(it.returnType.name)
                val funcName = it.name
                val className = it.declaringClass.name
                val signature = it.signature
                val sig = getSignature(it)
                logger.info("set native hook to $sig")
                if (retTypeName == "Void"){
                    it.setBody("{jmp0.app.interceptor.mtd.CallBridge" +
                            ".nativeCalled(\"${androidEnvironment.id}\",\"$className\",\"$funcName\",\"$signature\",\$args);}")
                }else{
                    it.setBody("{return ($retTypeName) jmp0.app.interceptor.mtd.CallBridge" +
                            ".nativeCalled(\"${androidEnvironment.id}\",\"$className\",\"$funcName\",\"$signature\",\$args);}")
                }

            }
        }
        return ctClass
    }
}