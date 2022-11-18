package jmp0.app.mock.system

import jmp0.app.DbgContext
import jmp0.app.classloader.XAndroidClassLoader
import jmp0.app.conversation.AppdbgConversationSchemaEnum
import jmp0.app.conversation.impl.ntv.NativeData
import jmp0.app.mock.annotations.ClassReplaceTo
import jmp0.util.SystemReflectUtils.findSystemClass
import jmp0.util.reflection
import org.apache.log4j.Logger
import java.io.InputStream
import java.io.PrintStream
import java.lang.System

// TODO: 2022/3/11 patch reflect framework，don't permit to invoke private method in android framework

//first system class I replaced, congratulation!!!
@ClassReplaceTo("java.lang.System")
class System {
    // this class load by xclassloadr
    // if you want to use system class or method use SystemReflectUtils to get
    companion object {

        private val logger = Logger.getLogger(System::class.java)

        @JvmField
        val out: PrintStream = xGetSystemStd("out") as PrintStream

        @JvmField
        val `in`: InputStream = xGetSystemStd("in") as InputStream

        @JvmField
        val err: PrintStream = xGetSystemStd("err") as PrintStream

        @JvmStatic //the annotation is important
        fun loadLibrary(libName: String) {
            val ae = DbgContext.getAndroidEnvironmentWithClassLoader(this::class.java.classLoader as XAndroidClassLoader)
            logger.info("want to load $libName...")
            ae.getConversationHandler(AppdbgConversationSchemaEnum.NATIVE)?.appdbgConversationHandle(ae,NativeData(libName))
        }

        @JvmStatic
        fun currentTimeMillis(): Long =
            reflection(DbgContext.appClassLoader, "java.lang.System") {
                method("currentTimeMillis")(null)
            } as Long

        @JvmStatic
        fun arraycopy(any: Any, a: Int, b: Any, c: Int, d: Int) {
            reflection(DbgContext.appClassLoader, "java.lang.System") {
                method(
                    "arraycopy",
                    Any::class.java,
                    Int::class.java,
                    Any::class.java,
                    Int::class.java,
                    Int::class.java
                )(
                    null,
                    any, a, b, c, d
                )
            }
        }

        @JvmStatic
        fun arraycopy(any: IntArray, a: Int, b: IntArray, c: Int, d: Int) {
            java.lang.System.arraycopy(any as Any,a,b as Any,c,d)
        }

        @JvmStatic
        fun arraycopy(any: ByteArray, a: Int, b: ByteArray, c: Int, d: Int) {
            java.lang.System.arraycopy(any as Any,a,b as Any,c,d)
        }

        @JvmStatic
        fun getProperty(property: String): String {
            return reflection(DbgContext.appClassLoader, "java.lang.System") {
                method("getProperty", String::class.java)(null, property) as String? ?: ""
            } as String
        }

        @JvmStatic
        fun getProperty(a: String, b: String): String {
            return reflection(DbgContext.appClassLoader, "java.lang.System") {
                method("getProperty", String::class.java, String::class.java)(null, a, b) as String? ?: ""
            } as String
        }

        @JvmStatic
        fun nanoTime(): Long =
            reflection(DbgContext.appClassLoader, "java.lang.System") {
                method("nanoTime")(null)
            } as Long

        @JvmStatic
        fun lineSeparator(): String =
            reflection(DbgContext.appClassLoader, "java.lang.System") {
                method("lineSeparator")(null)
            } as String

        @JvmStatic
        fun getenv(env: String): String? {
            return when (env) {
                "ANDROID_ROOT" -> null
                "ANDROID_DATA" -> "/data"
                "ANDROID_STORAGE" -> "/storage"
                "OEM_ROOT" -> null
                "VENDOR_ROOT" -> null
                else -> {
                    logger.warn("getenv $env just return from host")
                    reflection(DbgContext.appClassLoader, "java.lang.System") {
                        method("getenv", String::class.java)(null, env)
                    } as String?
                }
            }
        }

        @JvmStatic
        fun setProperty(a: String, b: String): String {
            logger.warn("System setProperty called,[$a,$b]")
            return reflection(DbgContext.appClassLoader, "java.lang.System") {
                method("setProperty", String::class.java, String::class.java)(null, a, b) ?: ""
            } as String
        }

        private fun xGetSystemStd(std: String): Any =
            "java.lang.System".findSystemClass().getDeclaredField(std).get(null)
    }
}