package jmp0.app.interceptor

import javassist.ClassPool
import javassist.Modifier
import org.apache.log4j.Logger


class ClassNativeInterceptor(data:ByteArray) {
    private val logger = Logger.getLogger(javaClass)
    private val ctClass = ClassPool.getDefault().makeClass(data.inputStream())

    private fun checkNativeFlag(modifiers:Int ) =
        (modifiers and Modifier.NATIVE) == Modifier.NATIVE

    private fun eraseNativeFlag(modifiers: Int) =
        (modifiers and (Modifier.NATIVE.inv()))


    fun doChange():ByteArray{
        ctClass.declaredMethods.forEach {
            //判断是否为native函数
            if(checkNativeFlag(it.modifiers)){
                //创建到java的bridge
                it.modifiers = eraseNativeFlag(it.modifiers)
                // TODO: 2022/3/7 type may not be loaded
                val retTypeName = it.returnType.name
                val funcName = it.name
                val className = it.declaringClass.name
                it.setBody("{\n"+
                    "return ($retTypeName) jmp0.app.AndroidEnvironment.Companion.getGNativeInterceptor()\n"+
                        ".nativeCalled(\"$className\",\"$funcName\",\$args);"+
                "}")

            }
        }
        return ctClass.toBytecode()
    }
}