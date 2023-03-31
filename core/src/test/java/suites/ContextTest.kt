package suites

import TestBase
import TestUtil
import jmp0.app.AndroidEnvironment
import jmp0.app.interceptor.intf.IInterceptor
import org.junit.jupiter.api.Test

class ContextTest:TestBase() {
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

        val contextClazz = androidEnvironment.findClass("android.content.Context")
        val ret = androidEnvironment.loadClass("jmp0.test.testapp.TestContext").run {
            val ins = getDeclaredConstructor(contextClazz).newInstance(androidEnvironment.context)
            getDeclaredMethod("testAll").invoke(ins)

        }
    }
}