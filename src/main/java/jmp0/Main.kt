package jmp0
import javassist.ClassPool
import javassist.CtClass
import jmp0.apk.ApkFile
import jmp0.app.AndroidEnvironment
import jmp0.app.DbgContext
import jmp0.app.XAndroidDexClassLoader
import jmp0.app.interceptor.mtd.INativeInterceptor
import jmp0.app.interceptor.runtime.AndroidRuntimeClassInterceptorBase
import jmp0.util.SystemReflectUtils.invokeEx
import org.apache.log4j.Logger
import sun.misc.GC
import java.io.File
import kotlin.concurrent.thread

class Main {
    /**
     * vm option => -Xverify:none
     * jdk_path/...../libjvm.dylib
     * find "Prohibited package name:" in libjvm.dylib
     * or
     * Prohibited package for non-bootstrap classes: %s from %s
     * and change "java/" to "patch" before the string
     * macos:
     * need to resign
     * find => security find-identity -v -p codesigning
     * resign => codesign -f -s "Apple Development:xxxx" libjvm.dylib
     */
    companion object{
        val logger = Logger.getLogger(javaClass)
        fun test(){
            val androidEnvironment = AndroidEnvironment(ApkFile(File("test-app/build/outputs/apk/debug/test-app-debug.apk")),
                absAndroidRuntimeClass = object : AndroidRuntimeClassInterceptorBase(){
                    override fun afterFindClassFile(androidEnvironment: AndroidEnvironment, ctClass: CtClass): CtClass {
                        super.afterFindClassFile(androidEnvironment,ctClass)
                        //insert your code
                        return ctClass
                    }

                    override fun beforeResolveClass(
                        androidEnvironment: AndroidEnvironment,
                        className: String,
                        classLoader: XAndroidDexClassLoader
                    ): Class<*>? {
                        return super.beforeResolveClass(androidEnvironment,className, classLoader)
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

            val TestJavaclazz = androidEnvironment.loadClass("jmp0.test.testapp.TestKotlin")
            val ins = TestJavaclazz.getConstructor().newInstance()
            val ret = TestJavaclazz.getDeclaredMethod("testString").invokeEx(ins)
            logger.debug(ret)
            androidEnvironment.destroy()
        }
        @JvmStatic
        fun main(args:Array<String>){
                test()
            }
        }
}