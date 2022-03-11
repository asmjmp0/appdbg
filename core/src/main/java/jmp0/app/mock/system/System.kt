package jmp0.app.mock.system

import jmp0.util.SystemReflectUtils.findSystemClass
import org.apache.log4j.Logger
import java.io.InputStream
import java.io.PrintStream
import java.lang.System

// TODO: 2022/3/11 patch reflect framework，don't permit to invoke private method in android framework

//first system class I replaced, congratulation!!!
class System  {
    // this class load by xclassloadr
    // if you want to use system class or method use SystemReflectUtils to get
    companion object{

        private val logger = Logger.getLogger(System::class.java)

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
        fun currentTimeMillis(): Long =
            java.lang.System.currentTimeMillis()

        @JvmStatic
        fun arraycopy(any: Any,a:Int,b:Any,c:Int,d:Int){
            java.lang.System.arraycopy(any, a, b, c, d)
        }

        @JvmStatic
        fun getProperty(property: String): String {
            return java.lang.System.getProperty(property)
        }

        @JvmStatic
        fun getProperty(a:String,b:String):String{
            return java.lang.System.getProperty(a,b)
        }

        @JvmStatic
        fun nanoTime(): Long {
            return java.lang.System.nanoTime()
        }

        @JvmStatic
        fun lineSeparator(): String {
            return java.lang.System.lineSeparator()
        }

        private fun xGetSystemStd(std: String):Any =
            xxClassName.findSystemClass().getDeclaredField(std).get(null)
    }
}