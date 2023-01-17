package jmp0
import javassist.CtClass
import jmp0.apk.ApkFile
import jmp0.apk.config.DefaultApkConfig
import jmp0.apk.config.IApkConfig
import jmp0.app.AndroidEnvironment
import jmp0.app.conversation.AppdbgConversationSchemaEnum
import jmp0.app.conversation.IAppdbgConversation
import jmp0.app.conversation.IAppdbgConversationHandler
import jmp0.app.conversation.impl.sp.SharedPreferencesConversation
import jmp0.app.interceptor.intf.IInterceptor
import jmp0.app.interceptor.intf.RuntimeClassInterceptorBase
import jmp0.app.interceptor.unidbg.UnidbgInterceptor
import jmp0.util.SystemReflectUtils.invokeEx
import jmp0.util.reflection
import org.apache.log4j.Logger
import java.io.File

// TODO: 2022/3/11 整理log factory
class Main {
    /**
     * vm option => -Xverify:none
     * jdk_path/...../libjvm.dylib
     * find "Prohibited package name:" in libjvm.dylib
     * jdk11 try to load
     * or
     * Prohibited package for non-bootstrap classes: %s from %s
     * and change "java/" to "patch" before the string
     * osx:
     * need to resign
     * find => security find-identity -v -p codesigning
     * resign => codesign -f -s "Apple Development:xxxx" libjvm.dylib
     */
    companion object {
        val logger = Logger.getLogger(javaClass)
        fun getBaseAndroidEnv(apkConfig: IApkConfig = DefaultApkConfig()) =
            AndroidEnvironment(ApkFile(File( "test-app/build/outputs/apk/debug/test-app-debug.apk"),apkConfig),
                object : IInterceptor {
                    override fun nativeCalled(
                        uuid: String,
                        className: String,
                        funcName: String,
                        signature: String,
                        param: Array<out Any?>
                    ): IInterceptor.ImplStatus {
                        return IInterceptor.ImplStatus(false,null)
                    }

                    override fun methodCalled(
                        uuid: String,
                        className: String,
                        funcName: String,
                        signature: String,
                        param: Array<out Any?>
                    ): Any? {
                        return Thread.currentThread().contextClassLoader
                    }

                    override fun ioResolver(path: String): IInterceptor.ImplStatus {
                        return IInterceptor.ImplStatus(false,"");
                    }

                })

        fun testLooper() {
            val androidEnvironment =
                AndroidEnvironment(ApkFile(File("test-app/build/outputs/apk/debug/test-app-debug.apk")),
                    methodInterceptor = object : IInterceptor {
                        override fun nativeCalled(
                            uuid: String,
                            className: String,
                            funcName: String,
                            signature: String,
                            param: Array<out Any?>
                        )
                                : IInterceptor.ImplStatus {
                            // TODO: 2022/3/7 use unidbg emulate native func.it should be loaded by android classloader
                            return if ((className == "com.example.myapplication.TestJava") and (funcName == "stringFromJNI"))
                                IInterceptor.ImplStatus(true, "hooked by asmjmp0")
                            else
                                IInterceptor.ImplStatus(false, null)
                        }

                        override fun methodCalled(
                            uuid: String,
                            className: String,
                            funcName: String,
                            signature: String,
                            param: Array<out Any?>
                        ): Any? {
                            if (funcName == "testString") {
                                logger.debug("class")
                                return null
                            }
                            return null
                        }

                        override fun ioResolver(path: String): IInterceptor.ImplStatus {
                            return IInterceptor.ImplStatus(false,"");
                        }

                    })
            //
//            androidEnvironment.registerMethodHook("jmp0.test.testapp.MainActivity.getStr()V",false)
            val TestJavaclazz = androidEnvironment.loadClass("jmp0.test.testapp.TestKotlin")

            val ret = TestJavaclazz.getDeclaredMethod("testLopper")
                .invokeEx(TestJavaclazz.getConstructor().newInstance())

            logger.debug("testLopper => $ret")
        }

        fun testBase64() {
            val androidEnvironment =
                AndroidEnvironment(ApkFile(File("test-app/build/outputs/apk/debug/test-app-debug.apk")),
                    object : IInterceptor {
                        override fun nativeCalled(
                            uuid: String,
                            className: String,
                            funcName: String,
                            signature: String,
                            param: Array<out Any?>
                        ): IInterceptor.ImplStatus {
                            TODO("Not yet implemented")
                        }

                        override fun methodCalled(
                            uuid: String,
                            className: String,
                            funcName: String,
                            signature: String,
                            param: Array<out Any?>
                        ): Any? {
                            TODO("Not yet implemented")
                        }

                        override fun ioResolver(path: String): IInterceptor.ImplStatus {
                            return IInterceptor.ImplStatus(false,"");
                        }

                    })
            val clazz = androidEnvironment.loadClass("jmp0.test.testapp.TestKotlin")
            val ins = clazz.getDeclaredConstructor().newInstance()
            val res = clazz.getDeclaredMethod("testBase64").invokeEx(ins)
            logger.info(res)
            androidEnvironment.destroy()
        }

        fun testJni() {
            val androidEnvironment =
                AndroidEnvironment(ApkFile(File("test-app/build/outputs/apk/debug/test-app-debug.apk"),object :IApkConfig{
                    override fun forceDecompile(): Boolean {
                        return false
                    }

                    override fun generateJarFile(): Boolean {
                        return true
                    }

                    override fun jarWithDebugInfo(): Boolean {
                        return true
                    }

                }),
                    object : UnidbgInterceptor(true){
                        override fun otherNativeCalled(uuid: String, className: String, funcName: String,
                            signature: String, param: Array<out Any?>
                        ): IInterceptor.ImplStatus {
                            TODO("Not yet implemented")
                        }

                        override fun methodCalled(uuid: String, className: String, funcName: String,
                                                  signature: String, param: Array<out Any?>
                        ): Any? {
                            logger.info("$className.$funcName$signature called")
                            return null
                        }

                        override fun ioResolver(path: String): IInterceptor.ImplStatus {
                            return IInterceptor.ImplStatus(false,"");
                        }

                    })
            androidEnvironment.addAfterClassInterceptor(object :RuntimeClassInterceptorBase(androidEnvironment){
                override fun doChange(ctClass: CtClass): CtClass {
                    println("test->"+ctClass.name)
                    return ctClass
                }
            })
            androidEnvironment.registerMethodHook("jmp0.test.testapp.TestNative.testAll()V",false);
            reflection(androidEnvironment.getClassLoader(),"jmp0.test.testapp.TestNative"){
                constructor()()
                method("testAll")(this.ins)
            }
            androidEnvironment.destroy()
        }

        fun testNetWork(){
            val ae = getBaseAndroidEnv()
            val ret = ae.loadClass("jmp0.test.testapp.net.TestNetWork").run {
                val ins = getDeclaredConstructor().newInstance()
                getDeclaredMethod("test").invoke(ins)

            }
            logger.info(ret)
        }

        fun testContext(){
            val ae = getBaseAndroidEnv()
            val contextClazz = ae.findClass("android.content.Context")
            val ret = ae.loadClass("jmp0.test.testapp.TestContext").run {
                val ins = getDeclaredConstructor(contextClazz).newInstance(ae.context)
                getDeclaredMethod("testAll").invoke(ins)

            }
        }

        fun testAES(){
            val ae = getBaseAndroidEnv(object:IApkConfig{
                override fun forceDecompile(): Boolean = false

                override fun generateJarFile(): Boolean = true

                override fun jarWithDebugInfo(): Boolean = true

            })
            val clazz = ae.loadClass("jmp0.test.testapp.TestAES")
            val ins = clazz.getDeclaredConstructor().newInstance()
            val ret = clazz.getDeclaredMethod("testAll").invoke(ins)
            logger.info(ret)
        }

        fun testFile(){
            val ae = AndroidEnvironment(ApkFile(File("test-app/build/outputs/apk/debug/test-app-debug.apk")),
                object : IInterceptor {
                    override fun nativeCalled(
                        uuid: String,
                        className: String,
                        funcName: String,
                        signature: String,
                        param: Array<out Any?>
                    ): IInterceptor.ImplStatus {
                        return IInterceptor.ImplStatus(false,null)
                    }

                    override fun methodCalled(
                        uuid: String,
                        className: String,
                        funcName: String,
                        signature: String,
                        param: Array<out Any?>
                    ): Any? {
                        logger.info("$className.$funcName$signature called")
                        return null
                    }

                    override fun ioResolver(path: String): IInterceptor.ImplStatus {
                        if (path == "/proc/self/maps"){
                            return IInterceptor.ImplStatus(true,"temp/test-app-debug.apk/assets/hello")
                        }
                        return IInterceptor.ImplStatus(false,"");
                    }

                })
            val clazz = ae.loadClass("jmp0.test.testapp.FileTest")
            val ins = clazz.getDeclaredConstructor().newInstance()
            clazz.getDeclaredMethod("testAll").invoke(ins)
        }

        fun testSharedPreferences(){
            val ae = AndroidEnvironment(ApkFile(File("test-app/build/outputs/apk/debug/test-app-debug.apk")),
                object : IInterceptor {
                    override fun nativeCalled(
                        uuid: String,
                        className: String,
                        funcName: String,
                        signature: String,
                        param: Array<out Any?>
                    ): IInterceptor.ImplStatus {
                        return IInterceptor.ImplStatus(false,null)
                    }

                    override fun methodCalled(
                        uuid: String,
                        className: String,
                        funcName: String,
                        signature: String,
                        param: Array<out Any?>
                    ): Any? {
                        logger.info("$className.$funcName$signature called")
                        return null
                    }

                    override fun ioResolver(path: String): IInterceptor.ImplStatus {
                        if (path == "/proc/self/maps"){
                            return IInterceptor.ImplStatus(true,"temp/test-app-debug.apk/assets/hello")
                        }
                        return IInterceptor.ImplStatus(false,"");
                    }

                })
            ae.setConversationHandler(AppdbgConversationSchemaEnum.SHARED_PREFERENCES,object :IAppdbgConversationHandler{
                override fun appdbgConversationHandle(
                    androidEnvironment: AndroidEnvironment,
                    conversation: IAppdbgConversation<*>
                ): IInterceptor.ImplStatus {
                    with(conversation as SharedPreferencesConversation){
                        val keyName = conversation.data.params[0] as String
                        when(conversation.data.methodName){
                            "getString"->{
                                if (keyName == "test1"){
                                    return IInterceptor.ImplStatus(true,"SharedPreferencesConversation Test")
                                }
                            }
                            "getInt"->{
                                if (keyName == "test2"){
                                    return IInterceptor.ImplStatus(true,666)
                                }
                            }

                        }
                    }
                    return IInterceptor.ImplStatus(false,null)
                }
            })
            val contextClazz = ae.findClass("android.content.Context")
            val clazz = ae.loadClass("jmp0.test.testapp.SharedPreferencesTest")
            val ins = clazz.getDeclaredConstructor(contextClazz).newInstance(ae.context)
            clazz.getDeclaredMethod("testAll").invoke(ins)
        }

        fun testReflection(){
            val ae = AndroidEnvironment(ApkFile(File("test-app/build/outputs/apk/debug/test-app-debug.apk")),
                object : IInterceptor {
                    override fun nativeCalled(
                        uuid: String,
                        className: String,
                        funcName: String,
                        signature: String,
                        param: Array<out Any?>
                    ): IInterceptor.ImplStatus {
                        return IInterceptor.ImplStatus(false,null)
                    }

                    override fun methodCalled(
                        uuid: String,
                        className: String,
                        funcName: String,
                        signature: String,
                        param: Array<out Any?>
                    ): Any? {
                        logger.info("$className.$funcName$signature called")
                        return null
                    }

                    override fun ioResolver(path: String): IInterceptor.ImplStatus {
                        TODO("Not yet implemented")
                    }

                })

            val clazz = ae.findClass("jmp0.test.testapp.reflection.TestReflection")
            val method = clazz.getDeclaredMethod("testAll")
            method.invoke(null)
        }

        fun testDebug(){
            val ae = getBaseAndroidEnv(object:IApkConfig{
                override fun forceDecompile(): Boolean = false

                override fun generateJarFile(): Boolean = true

                override fun jarWithDebugInfo(): Boolean = true

            })

            val clazz = ae.findClass("jmp0.test.testapp.DebugTest")
            val instance:Any = clazz.getDeclaredConstructor(Int::class.java,String::class.java).newInstance(10,"AAAA")
            clazz.getDeclaredMethod("testAll",Int::class.java).invoke(instance,1)
        }

        @JvmStatic
        fun main(args: Array<String>) {
//            Logger.getLogger(jmp0.app.classloader.XAndroidClassLoader::class.java).level = Level.TRACE
//            Logger.getLogger(jmp0.app.mock.ntv.SystemProperties::class.java).level = Level.TRACE
//            val reflections = Reflections(CommonConf.Mock.mockNativeClassPackageName)
//            val ls = reflections.getTypesAnnotatedWith(NativeHookClass::class.java)
//            ls.forEach {
//                println(it)
//
//            }

//            testPdd()
//            testContext()
//            testLooper()
//            testBase64()
//            testJni()
//            testNetWork()
//            testAES()
//            testFile()
            testSharedPreferences()
//            testReflection()
//            testDebug()
            // test-app/build/intermediates/javac/debug/classes/jmp0/test/testapp/DebugTest.class debug info
//             temp/test-app-debug.apk/classes/jmp0/test/testapp/DebugTest.class without debug info
//            AppdbgDecompiler(File("temp/test-app-debug.apk/classes/jmp0/test/testapp/DebugTest.class")).decompile()
        }
    }
}