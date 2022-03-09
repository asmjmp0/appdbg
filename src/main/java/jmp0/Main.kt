package jmp0
import android.os.Handler
import android.os.Looper
import android.os.Message
import javassist.CtClass
import jmp0.apk.ApkFile
import jmp0.app.AndroidEnvironment
import jmp0.app.XAndroidClassLoader
import jmp0.app.interceptor.intf.IInterceptor
import jmp0.app.clazz.ClassLoadedCallbackBase
import jmp0.app.clazz.android.AppCompatActivity
import jmp0.util.SystemReflectUtils.getMethodWithSignature
import jmp0.util.SystemReflectUtils.invokeEx
import libcore.io.Linux
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
            val androidEnvironment = AndroidEnvironment(ApkFile(File("test-app/build/outputs/apk/debug/test-app-debug.apk"),false),
                absAndroidRuntimeClass = object : ClassLoadedCallbackBase(){
                    override fun afterResolveClassImpl(androidEnvironment: AndroidEnvironment, ctClass: CtClass): CtClass {
                        return ctClass
                    }
                    override fun beforeResolveClassImpl(androidEnvironment: AndroidEnvironment,
                        className: String,
                        classLoader: XAndroidClassLoader
                    ): Class<*>? {
                        return null
                    }
                },
                methodInterceptor =  object : IInterceptor {
                    override fun nativeCalled(className: String, funcName: String, signature: String, param: Array<out Any?>)
                    : IInterceptor.ImplStatus {
                        // TODO: 2022/3/7 use unidbg emulate native func.it should be loaded by android classloader
                        return if ((className =="com.example.myapplication.TestJava") and (funcName == "stringFromJNI"))
                            IInterceptor.ImplStatus(true,"hooked by asmjmp0")
                        else
                            IInterceptor.ImplStatus(false,null)
                    }

                    override fun methodCalled(className: String, funcName: String, signature: String, param: Array<out Any?>): Any? {
                        if (funcName == "testString"){
                            logger.debug("class")
                            return null
                        }
                        return null
                    }

                })
            //
//            androidEnvironment.registerMethodHook("jmp0.test.testapp.MainActivity.getStr()V",false)
            val TestJavaclazz = androidEnvironment.loadClass("jmp0.test.testapp.TestKotlin")
            val contextIns = androidEnvironment.loadClassProject("jmp0.app.clazz.android.AppCompatActivity").getConstructor().newInstance()
            var ret = TestJavaclazz.getDeclaredMethod("testString",
                androidEnvironment.findClass("android.content.Context"))
                .invokeEx( TestJavaclazz.getConstructor().newInstance(), contextIns)
            logger.debug("testString => $ret")

            ret = TestJavaclazz.getDeclaredMethod("testLopper")
                .invokeEx( TestJavaclazz.getConstructor().newInstance())

            logger.debug("testLopper => $ret")



//            androidEnvironment.destroy()
        }
        fun testBase64(){
            val androidEnvironment = AndroidEnvironment(ApkFile(File("test-app/build/outputs/apk/debug/test-app-debug.apk"),false),
            object :IInterceptor{
                override fun nativeCalled(
                    className: String,
                    funcName: String,
                    signature: String,
                    param: Array<out Any?>
                ): IInterceptor.ImplStatus {
                    TODO("Not yet implemented")
                }

                override fun methodCalled(
                    className: String,
                    funcName: String,
                    signature: String,
                    param: Array<out Any?>
                ): Any? {
                    TODO("Not yet implemented")
                }

            })
            val clazz = androidEnvironment.loadClass("jmp0.test.testapp.TestKotlin")
            val ins = clazz.getDeclaredConstructor().newInstance()
            val res = clazz.getDeclaredMethod("testBase64").invokeEx(ins)
            logger.debug(res)
        }
        @JvmStatic
        fun main(args:Array<String>){
            Main::class.java.getMethodWithSignature("android.util.Log.println_native(IILjava/lang/String;Ljava/lang/String;)[I")
//            test()
//            testBase64()

        }
        }
}