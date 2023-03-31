import jmp0.apk.ApkFile
import jmp0.apk.config.IApkConfig
import jmp0.app.mock.ntv.Binder
import org.apache.log4j.Logger

object TestUtil {
    val testApkFile = ApkFile(this.javaClass.classLoader.getResource("appdbg-test.apk")!!.openStream(),"appdbg-test.apk",object :IApkConfig{
        override fun forceDecompile(): Boolean = false
        override fun generateJarFile(): Boolean = true
        override fun jarWithDebugInfo(): Boolean = true
    })

    val logger = Logger.getLogger(TestUtil::class.java)
}