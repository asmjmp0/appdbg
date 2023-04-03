package suites

import TestBase
import TestUtil
import jmp0.app.AndroidEnvironment
import jmp0.app.IAndroidInvokeFile
import jmp0.app.interceptor.intf.IInterceptor
import org.junit.jupiter.api.Test

class AESTest:TestBase(),IAndroidInvokeFile {

    @Test
    override fun test() {
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

        val clazz = ae.loadClass("jmp0.test.testapp.TestAES")
        val ins = clazz.getDeclaredConstructor().newInstance()
        val ret = clazz.getDeclaredMethod("testAll").invoke(ins)
        TestUtil.logger.info(ret)

//        ae.runInvokeFile(this)
        ae.destroy()
    }

    override fun run(androidEnvironment: AndroidEnvironment) {
//        val ret = jmp0.test.testapp.TestAES().testAll()
//        TestUtil.logger.info(ret)
    }
}