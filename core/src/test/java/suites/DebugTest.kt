package suites

import TestBase
import jmp0.app.AndroidEnvironment
import jmp0.app.IAndroidInvokeFile
import jmp0.app.interceptor.intf.IInterceptor
import org.junit.jupiter.api.Test

class DebugTest:TestBase(),IAndroidInvokeFile {

    @Test override fun test() {
        val ae = AndroidEnvironment(TestUtil.testApkFile, object : IInterceptor {
            override fun nativeCalled(
                uuid: String,
                className: String,
                funcName: String,
                signature: String,
                param: Array<Any?>
            ): IInterceptor.ImplStatus {
                return IInterceptor.ImplStatus(false,null)
            }

            override fun methodCalled(
                uuid: String,
                className: String,
                instance: Any?,
                funcName: String,
                signature: String,
                param: Array<Any?>
            ): Any? {
                TODO("Not yet implemented")
            }

            override fun ioResolver(path: String): String? {
                return null
            }

        })
        val clazz = ae.findClass("jmp0.test.testapp.DebugTest")
        val instance:Any = clazz.getDeclaredConstructor(Int::class.java,String::class.java).newInstance(10,"AAAA")
        clazz.getDeclaredMethod("testAll",Int::class.java).invoke(instance,1)
//        ae.runInvokeFile(this)
        ae.destroy()
    }

    override fun run(androidEnvironment: AndroidEnvironment) {
//        jmp0.test.testapp.DebugTest(10,"AAAA").testAll(1)
    }
}