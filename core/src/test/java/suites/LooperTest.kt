package suites

import TestBase
import TestUtil
import jmp0.app.AndroidEnvironment
import jmp0.app.interceptor.intf.IInterceptor
import jmp0.util.SystemReflectUtils.invokeEx
import org.junit.jupiter.api.Test

class LooperTest:TestBase() {

    @Test
    override fun test(){
        val androidEnvironment = AndroidEnvironment(
            TestUtil.testApkFile, object : IInterceptor {
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

        val TestJavaclazz = androidEnvironment.loadClass("jmp0.test.testapp.TestKotlin")

        val ret = TestJavaclazz.getDeclaredMethod("testLopper")
            .invokeEx(TestJavaclazz.getConstructor().newInstance())

        TestUtil.logger.debug("testLopper => $ret")
    }
}