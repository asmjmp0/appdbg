package jmp0.app.mock.ntv

import jmp0.app.DbgContext
import jmp0.app.mock.NativeHookClass
import jmp0.util.SystemReflectUtils
import org.apache.log4j.Logger

@NativeHookClass("dalvik.system.VMRuntime")
object VMRuntime {
    private val  logger = Logger.getLogger(VMRuntime::class.java)
    @JvmStatic
    fun is64Bit(uuid:String) = true

    @JvmStatic
    fun newUnpaddedArray(uuid: String,clazz: Class<*>,len: Int):Any{
        val env = DbgContext.getAndroidEnvironment(uuid)
        try {
            return java.lang.reflect.Array.newInstance(clazz,len)
        }catch (e:java.lang.Exception){
            val dClass = env!!.findClass(clazz.name)
            return java.lang.reflect.Array.newInstance(dClass,len)
        }

    }

    @JvmStatic
    fun registerAppInfo(uuid: String,packageName:String,dataDir:String,processName:String){
        logger.debug("registerAppInfo called")
    }

    @JvmStatic
    fun clampGrowthLimit(uuid: String){
        logger.debug("clampGrowthLimit called")
    }

}