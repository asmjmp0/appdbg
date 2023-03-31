package suites

import TestBase
import jmp0.app.AndroidEnvironment
import jmp0.app.interceptor.intf.IInterceptor
import org.junit.jupiter.api.Test

class ReflectionTest:TestBase() {

    @Test override fun test() {
        val ae = AndroidEnvironment(TestUtil.testApkFile, object : IInterceptor {
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
                TODO("Not yet implemented")
            }

            override fun ioResolver(path: String): IInterceptor.ImplStatus {
                return IInterceptor.ImplStatus(false,"");
            }

        })

        val clazz = ae.findClass("jmp0.test.testapp.reflection.TestReflection")
        val method = clazz.getDeclaredMethod("testAll")
        method.invoke(null)
    }
}