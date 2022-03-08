package jmp0
import javassist.ClassPool
import javassist.CtClass
import jmp0.apk.ApkFile
import jmp0.app.AndroidEnvironment
import jmp0.app.XAndroidDexClassLoader
import jmp0.app.interceptor.mtd.INativeInterceptor
import jmp0.app.interceptor.runtime.AndroidRuntimeClassInterceptorBase
import jmp0.util.SystemReflectUtils.invokeEx
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

            val MainActivityClazz = androidEnvironment.loadClass("com.example.myapplication.MainActivity")!!
            logger.debug(MainActivityClazz.getConstructor().newInstance())


            val TestJavaclazz = androidEnvironment.loadClass("com.example.myapplication.TestJava")!!
            val ins = TestJavaclazz.getConstructor().newInstance()
            val ret = TestJavaclazz.getDeclaredMethod("getStr").invokeEx(ins)
            logger.debug(ret)

        }
    }
}