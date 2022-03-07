package jmp0.app.interceptor.mtd

import javassist.CtClass
import jmp0.app.interceptor.runtime.RuntimeClassInterceptorBase
import org.apache.log4j.Logger


class ClassNativeInterceptor(private val ctClass: CtClass):RuntimeClassInterceptorBase(ctClass) {
    private val logger = Logger.getLogger(javaClass)

    override fun doChange():CtClass{
        ctClass.declaredMethods.forEach {
            //判断是否为native函数
            if(checkNativeFlag(it.modifiers)){
                //创建到java的bridge
                it.modifiers = eraseNativeFlag(it.modifiers)
                // TODO: 2022/3/7 处理 returnType为void的情况
                val retTypeName = it.returnType.name
                val funcName = it.name
                val className = it.declaringClass.name
                it.setBody("{\n"+
                    "return ($retTypeName) jmp0.app.AndroidEnvironment.Companion.getGNativeInterceptor()\n"+
                        ".nativeCalled(\"$className\",\"$funcName\",\$args);"+
                "}")

            }
        }
        return ctClass
    }
}