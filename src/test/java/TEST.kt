import com.googlecode.d2j.dex.Dex2jar
import com.googlecode.d2j.reader.DexFileReader
import jmp0.app.AndroidEnvironment
import jmp0.dex.XDexClassLoader
import org.junit.jupiter.api.Test
import java.io.File
import java.net.URLClassLoader

class TEST {

    @Test
    fun test(){
//        AndroidEnvironment().loader.loadClass()
//        XDexClassLoader.instance.toString()
//            .loadClass("android.util.Base64")
        val ret = AndroidEnvironment().loader.loadClass(File("test_data/classes/com_example_myapplication_MainActivity\$\$ExternalSyntheticLambda0.class"))
        val ins = ret?.getDeclaredConstructor()?.newInstance()
        val res = ret?.getDeclaredMethod("getStr")?.invoke(ins)
        println(ret)
    }
}