package suites

import TestBase
import TestUtil
import javassist.CtClass
import jmp0.app.AndroidEnvironment
import jmp0.app.IAndroidInvokeFile
import jmp0.app.interceptor.intf.IInterceptor
import jmp0.app.interceptor.intf.RuntimeClassInterceptorBase
import jmp0.app.interceptor.unidbg.UnidbgInterceptor
//import jmp0.test.testapp.TestNative
import jmp0.util.reflection
import org.junit.jupiter.api.Test

class JNITest:TestBase(),IAndroidInvokeFile {

    @Test
    override fun test(){
        val androidEnvironment = AndroidEnvironment(TestUtil.testApkFile,object : UnidbgInterceptor(TestUtil.testApkFile.copyApkFile,true){
                    override fun otherNativeCalled(uuid: String, className: String, funcName: String,
                                                   signature: String, param: Array<out Any?>
                    ): IInterceptor.ImplStatus {
                        TODO("Not yet implemented")
                    }

                    override fun methodCalled(uuid: String, className: String, funcName: String,
                                              signature: String, param: Array<out Any?>
                    ): Any? {
                        TestUtil.logger.info("$className.$funcName$signature called")
                        return null
                    }

                    override fun ioResolver(path: String): IInterceptor.ImplStatus {
                        return IInterceptor.ImplStatus(false,"");
                    }

                })
        androidEnvironment.addAfterClassInterceptor(object : RuntimeClassInterceptorBase(androidEnvironment){
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
//        androidEnvironment.runInvokeFile(this)
        androidEnvironment.destroy()
    }

    override fun run(androidEnvironment: AndroidEnvironment) {
//        TestNative().testAll()
    }
}