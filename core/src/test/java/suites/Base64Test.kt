package suites

import TestBase
import TestUtil
import jmp0.app.AndroidEnvironment
import jmp0.app.IAndroidInvokeFile
import jmp0.app.interceptor.intf.IInterceptor
import jmp0.test.testapp.TestKotlin
import jmp0.util.SystemReflectUtils.invokeEx
import org.junit.jupiter.api.Test

class Base64Test:TestBase(),IAndroidInvokeFile {

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
//        val clazz = androidEnvironment.loadClass("jmp0.test.testapp.TestKotlin")
//        val ins = clazz.getDeclaredConstructor().newInstance()
//        val res = clazz.getDeclaredMethod("testBase64").invokeEx(ins)
//        TestUtil.logger.info(res)
        androidEnvironment.runInvokeFile(this)
        androidEnvironment.destroy()
    }

    override fun run(androidEnvironment: AndroidEnvironment) {
        val ret = TestKotlin().testString(androidEnvironment.context as android.content.Context)
        TestUtil.logger.info(ret)
    }
}