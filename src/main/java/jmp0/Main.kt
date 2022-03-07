package jmp0
import javassist.ClassPool
import javassist.CtClass
import jmp0.apk.ApkFile
import jmp0.app.AndroidEnvironment
import jmp0.app.XAndroidDexClassLoader
import jmp0.app.interceptor.mtd.INativeInterceptor
import jmp0.app.interceptor.runtime.AndroidRuntimeClassInterceptorBase
import org.apache.log4j.Logger
import java.io.File

class Main {
    /**
     * vm option => -Xverify:none
     *  -Xbootclasspath/p:./libs/dbg_inject.jar
     */
    companion object{
        val logger = Logger.getLogger(javaClass)
        @JvmStatic
        fun main(args:Array<String>){
            System.setSecurityManager(null)
            val androidEnvironment = AndroidEnvironment(ApkFile(File("test_data/app-debug.apk")),
                absAndroidRuntimeClass = object : AndroidRuntimeClassInterceptorBase(){
                    override fun afterFindClassFile(ctClass: CtClass): CtClass {
                        super.afterFindClassFile(ctClass)
                        //insert your code
                        return ctClass
                    }

                    override fun beforeResolveClass(className: String, classLoader: XAndroidDexClassLoader): Class<*>? {
                        return super.beforeResolveClass(className, classLoader)
                    }
                },
                nativeInterceptor =  object : INativeInterceptor {
                override fun nativeCalled(className: String, funcName: String, param: Array<Any>): Any? {
                    // TODO: 2022/3/7 use unidbg emulate native func
                    return if ((className =="com.example.myapplication.TestJava")
                        and (funcName == "stringFromJNI"))
                        "jni hooked by jmp0 "
                    else
                        null
                }

                })
            //

            var ret = androidEnvironment.loadClass("com.example.myapplication.MainActivity")!!
            logger.debug(androidEnvironment.androidInvokeUtils.setNowClass(ret).getConstructor().newInstance())


//            var ret = androidEnvironment.loadClass("com.example.myapplication.TestJava")!!
//            logger.debug(androidEnvironment.androidInvokeUtils.setNowClass(ret).getConstructor().newInstance())
//            logger.debug(ret)
//            logger.info(androidEnvironment.androidInvokeUtils.setNowClass(ret).run {
//                invoke(ret.getDeclaredMethod("getStr"),newInstance(getConstructor(String::class.java),"123434")!!)
//            })

        }
    }
}