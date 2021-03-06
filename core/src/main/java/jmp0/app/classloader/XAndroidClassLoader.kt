package jmp0.app.classloader

import jmp0.app.AndroidEnvironment
import org.apache.log4j.Logger

/**
 * each application has their own classloader
 * which make program isolation possible
 */
class XAndroidClassLoader(private val androidEnvironment: AndroidEnvironment):ClassLoader(Thread.currentThread().contextClassLoader) {
    private val logger = Logger.getLogger(javaClass)

    override fun findClass(name: String): Class<*> {
        try {
            logger.trace("try to load $name")
            val res = findLoadedClass(name)
            if (res != null) return res
            return androidEnvironment.loadClass(name)
        }catch (e:Throwable){
            //try to load class
            return super.findClass(name)
        }
    }


    fun xDefineClass(name: String?,data:ByteArray,off:Int,size: Int):Class<*> =
        defineClass(name,data,0,size)
}