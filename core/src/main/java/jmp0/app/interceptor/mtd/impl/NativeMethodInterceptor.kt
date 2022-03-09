package jmp0.app.interceptor.mtd.impl

import javassist.CtClass
import javassist.NotFoundException
import jmp0.app.AndroidEnvironment
import jmp0.app.interceptor.intf.RuntimeClassInterceptorBase
import org.apache.log4j.Logger


class NativeMethodInterceptor(private val androidEnvironment: AndroidEnvironment, private val ctClass: CtClass)
    : RuntimeClassInterceptorBase(androidEnvironment,ctClass) {
    private val logger = Logger.getLogger(javaClass)

    override fun doChange():CtClass{
        ctClass.declaredMethods.forEach {
            //判断是否为native函数
            if(checkNativeFlag(it.modifiers)){
                //创建到java的bridge
                it.modifiers = eraseNativeFlag(it.modifiers)
                // TODO: 2022/3/9 单独抽函数
                var retTypeName:String? =null
                try {
                    retTypeName = replaceType(it.returnType.name)
                }catch (e:NotFoundException){
                    var name:String? =null
                    if (e.message!!.contains("[]")){
                        name = e.message!!.replace("[]","")
                        retTypeName = androidEnvironment.loadClass(name).name + "[]"
                    }else{
                        name = e.message!!
                        retTypeName = androidEnvironment.loadClass(name).name
                    }
                }
                val funcName = it.name
                val className = it.declaringClass.name
                val signature = it.signature
                val sig = getSignature(it)
                logger.info("set native hook to $sig")
                // TODO: 2022/3/9 单独抽函数
                try {
                    if (retTypeName == "Void"){
                        it.setBody("{jmp0.app.interceptor.mtd.CallBridge" +
                                ".nativeCalled(\"${androidEnvironment.id}\",\"$className\",\"$funcName\",\"$signature\",\$args);}")
                    }else{
                        it.setBody("{return ($retTypeName) jmp0.app.interceptor.mtd.CallBridge" +
                                ".nativeCalled(\"${androidEnvironment.id}\",\"$className\",\"$funcName\",\"$signature\",\$args);}")
                    }
                }catch (e:Exception){
                    if ("NotFound" in e.message!!){
                        val a = e.message!!.indexOf(":")
                        val className = e.message!!.substring(a+2,e.message!!.length)
                        androidEnvironment.loadClass(className)
                        if (retTypeName == "Void"){
                            it.setBody("{jmp0.app.interceptor.mtd.CallBridge" +
                                    ".nativeCalled(\"${androidEnvironment.id}\",\"$className\",\"$funcName\",\"$signature\",\$args);}")
                        }else{
                            it.setBody("{return ($retTypeName) jmp0.app.interceptor.mtd.CallBridge" +
                                    ".nativeCalled(\"${androidEnvironment.id}\",\"$className\",\"$funcName\",\"$signature\",\$args);}")
                        }
                    }else throw e

                }


            }
        }
        return ctClass
    }
}