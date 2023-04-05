package suites

import TestBase
import TestUtil
import jmp0.app.AndroidEnvironment
import jmp0.app.IAndroidInvokeFile
import jmp0.app.interceptor.intf.IInterceptor
import org.junit.jupiter.api.Test

class FileTest:TestBase(),IAndroidInvokeFile {

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
                    return null
                }

                override fun ioResolver(path: String): IInterceptor.ImplStatus {
                    if (path == "/proc/self/maps"){
                        return IInterceptor.ImplStatus(true,"../temp/appdbg-test.apk/assets/hello")
                    }
                    return IInterceptor.ImplStatus(false,"");
                }

            })
        val clazz = ae.loadClass("jmp0.test.testapp.FileTest")
        val ins = clazz.getDeclaredConstructor().newInstance()
        clazz.getDeclaredMethod("testAll").invoke(ins)
//        ae.runInvokeFile(this)
        ae.destroy()
    }

    override fun run(androidEnvironment: AndroidEnvironment) {
//        FileTest().testAll()
    }
}