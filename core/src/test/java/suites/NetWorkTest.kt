package suites

import TestBase
import TestUtil
import jmp0.app.AndroidEnvironment
import jmp0.app.IAndroidInvokeFile
import jmp0.app.interceptor.intf.IInterceptor
import org.junit.jupiter.api.Test

class NetWorkTest:TestBase(),IAndroidInvokeFile {

    override fun test(){
        val ae = AndroidEnvironment(
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
        val ret = ae.loadClass("jmp0.test.testapp.net.TestNetWork").run {
            val ins = getDeclaredConstructor().newInstance()
            getDeclaredMethod("test").invoke(ins)
        }

//        ae.runInvokeFile(this)
        ae.destroy()
    }

    override fun run(androidEnvironment: AndroidEnvironment) {
//        val ret = TestNetWork().test()
//        TestUtil.logger.info(ret)
    }
}