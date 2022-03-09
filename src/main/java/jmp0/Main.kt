package jmp0
import javassist.ClassPool
import javassist.CtClass
import jmp0.apk.ApkFile
import jmp0.app.AndroidEnvironment
import jmp0.app.XAndroidDexClassLoader
import jmp0.app.interceptor.intf.IInterceptor
import jmp0.app.clazz.ClassLoadedCallbackBase
import jmp0.util.SystemReflectUtils.invokeEx
import org.apache.log4j.Logger
import java.io.File

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
            val return_type = ClassPool.getDefault().makeClass("android.content.ContentResolver")
            val string_ret_type = ClassPool.getDefault().getCtClass("java.lang.String")
            val androidEnvironment = AndroidEnvironment(ApkFile(File("test-app/build/outputs/apk/debug/test-app-debug.apk"),false),
                absAndroidRuntimeClass = object : ClassLoadedCallbackBase(){
                    override fun afterResolveClassImpl(androidEnvironment: AndroidEnvironment, ctClass: CtClass): CtClass {
                        return ctClass
                    }
                    override fun beforeResolveClassImpl(androidEnvironment: AndroidEnvironment,
                        className: String,
                        classLoader: XAndroidDexClassLoader
                    ): Class<*>? {
                        return null
                    }
                },
                nativeInterceptor =  object : IInterceptor {
                    override fun nativeCalled(className: String, funcName: String, signature: String, param: Array<out Any?>)
                    : IInterceptor.ImplStatus {
                        // TODO: 2022/3/7 use unidbg emulate native func
                        return if ((className =="com.example.myapplication.TestJava") and (funcName == "stringFromJNI"))
                            IInterceptor.ImplStatus(true,"hooked by asmjmp0")
                        else
                            IInterceptor.ImplStatus(false,null)
                    }

                    override fun methodCalled(className: String, funcName: String, signature: String, param: Array<out Any?>): Any? {
                        if (funcName == "getStr"){
                            logger.debug("class")
                            return null
                        }
                        return null
                    }

                })
            //
            androidEnvironment.registerMethodHook("jmp0.test.testapp.MainActivity.getStr()V",false)

            val TestJavaclazz = androidEnvironment.loadClass("jmp0.test.testapp.MainActivity")
            val ins = TestJavaclazz.getConstructor().newInstance()
            val ret = TestJavaclazz.getDeclaredMethod("getStr").invokeEx(ins)
            logger.debug(ret)
//            androidEnvironment.destroy()
        }
        @JvmStatic
        fun main(args:Array<String>){
                test()
            }
        }
}