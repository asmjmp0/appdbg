package jmp0.app

import jmp0.app.AndroidEnvironment
import jmp0.app.clazz.ClassLoadedCallbackBase
import jmp0.conf.CommonConf
import org.apache.log4j.Logger
import java.io.File
import java.lang.Exception
import java.net.URL
import java.net.URLClassLoader

/**
 * each application has their own classloader
 * which make program isolation possible
 */
class XAndroidClassLoader(private val androidEnvironment: AndroidEnvironment, private val callbackBase: ClassLoadedCallbackBase):ClassLoader(Thread.currentThread().contextClassLoader) {
    private val logger = Logger.getLogger(javaClass)

    override fun findClass(name: String): Class<*> {
        try {
            logger.info("try to load $name")
            return super.findClass(name)
        }catch (e:Throwable){
            val res = findLoadedClass(name)
            if (res != null) return res
            //try to load class
            return androidEnvironment.loadClass(name)
        }
    }

    fun xDefineClass(name: String?,data:ByteArray,off:Int,size: Int):Class<*> =
        defineClass(name,data,0,size)
}