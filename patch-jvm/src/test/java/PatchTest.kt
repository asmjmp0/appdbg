import jmp0.patchjvm.PatchMain
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.File

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/4/7
 */
class PatchTest {

    @Test
    fun test(){
        PatchMain(File(System.getProperty("user.dir")).parentFile).patch()
        val bs = this.javaClass.classLoader.getResource("RandomAccess.class")!!.openStream().readBytes()
        try {
            TestClassLoader().testDefineClass(bs)
        }catch (e:Exception){
            assert(e !is SecurityException)
        }
    }
}