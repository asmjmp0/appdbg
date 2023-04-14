import jmp0.conf.CommonConf
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.File

abstract class TestBase {
    companion object {
        @JvmStatic
        @BeforeAll
        fun init() { CommonConf.workDir = File(System.getProperty("user.dir")).parent }
    }
    abstract fun test();
    @Test fun testReal() = test()
}