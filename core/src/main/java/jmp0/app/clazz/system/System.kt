package jmp0.app.clazz.system

import jmp0.util.SystemReflectUtils.findSystemClass
import org.apache.log4j.Logger
import java.io.InputStream
import java.io.PrintStream

//first system class I replaced, congratulation!!!
class System  {
    // this class load by xclassloadr
    // if you want to use system class or method use SystemReflectUtils to get
    companion object{

        private val logger = Logger.getLogger(javaClass)

        // const const const
        const val xxClassName: String = "java.lang.System"

        @JvmField
        val out:PrintStream = xGetSystemStd("out") as PrintStream

        @JvmField
        val `in`:InputStream = xGetSystemStd("in") as InputStream

        @JvmField
        val err:PrintStream = xGetSystemStd("err") as PrintStream

        @JvmStatic //the annotation is important
        fun loadLibrary(libName: String){
            logger.debug("want to load $libName...")
        }

        @JvmStatic
        fun currentTimeMillis():Long =
            java.lang.System.currentTimeMillis()

        @JvmStatic
        fun arraycopy(any: Any,a:Int,b:Any,c:Int,d:Int){
            java.lang.System.arraycopy(any,a,b,c,d)
        }

        private fun xGetSystemStd(std:String):Any =
            xxClassName.findSystemClass().getDeclaredField(std).get(null)
    }
}