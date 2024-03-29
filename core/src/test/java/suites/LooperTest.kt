package suites

import TestBase
import TestUtil
import jmp0.app.AndroidEnvironment
import jmp0.app.IAndroidInvokeFile
import jmp0.app.interceptor.intf.IInterceptor
//import jmp0.test.testapp.TestKotlin
import jmp0.util.SystemReflectUtils.invokeEx
import org.junit.jupiter.api.Test

class LooperTest:TestBase(),IAndroidInvokeFile {

    override fun test(){
        val androidEnvironment = AndroidEnvironment(
            TestUtil.testApkFile, object : IInterceptor {
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
        val TestJavaclazz = androidEnvironment.loadClass("jmp0.test.testapp.TestKotlin")

        val ret = TestJavaclazz.getDeclaredMethod("testLopper")
            .invokeEx(TestJavaclazz.getConstructor().newInstance())

        TestUtil.logger.debug("testLopper => $ret")
//        androidEnvironment.runInvokeFile(this)
        androidEnvironment.destroy()
    }

    override fun run(androidEnvironment: AndroidEnvironment) {
//        val ret = TestKotlin().testLopper()
//        TestUtil.logger.debug("testLopper => $ret")
    }
}