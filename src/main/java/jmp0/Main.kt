package jmp0

import com.sun.tools.attach.VirtualMachine
import jmp0.apk.ApkFile
import jmp0.app.AndroidEnvironment
import jmp0.app.AndroidInvokeUtils
import jmp0.app.interceptor.INativeInterceptor
import org.apache.log4j.Logger
import java.io.File
import java.lang.Exception

class Main {
    companion object{
        val logger = Logger.getLogger(javaClass)
        @JvmStatic
        fun main(args:Array<String>){
//            Logger.getRootLogger().level = Level
            val androidEnvironment = AndroidEnvironment(ApkFile(File("test_data/app-debug.apk")),object :INativeInterceptor{
                override fun nativeCalled(className: String, funcName: String, param: Array<Any>): Any? {
                    // TODO: 2022/3/7 use unidbg emulate native func
                    return if ((className =="com.example.myapplication.TestJava")
                        and (funcName == "stringFromJNI"))
                        "jni hooked by asmjmp0 "
                    else
                        null
                }

            })
            var ret = androidEnvironment.loadClass("com.example.myapplication.TestJava")!!
            val androidInvokeUtils = AndroidInvokeUtils(androidEnvironment).setNowClass(ret)
            logger.info(androidInvokeUtils.run {
                invoke(ret.getDeclaredMethod("getStr"),newInstance(getConstructor(String::class.java),"123434")!!)
            })

        }
    }
}