package jmp0.app

import jmp0.app.AndroidEnvironment
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
class XAndroidDexClassLoader(private val androidEnvironment: AndroidEnvironment):ClassLoader(Thread.currentThread().contextClassLoader) {
    private val logger = Logger.getLogger(javaClass)

    override fun findClass(name: String): Class<*>? {
        try {
            return super.findClass(name)
        }catch (e:ClassNotFoundException){
            val res = findLoadedClass(name)
            if (res != null) return res
            //try to load class
            return androidEnvironment.loadClass(name)
        }
    }

    // TODO: 2022/3/8 增加不同app 环境的异常throw
    fun xDefineClass(name: String?,data:ByteArray,off:Int,size: Int):Class<*> =
        defineClass(name,data,0,size)
}