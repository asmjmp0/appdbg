package jmp0.app.mock.ntv

import jmp0.app.mock.NativeHookClass
import org.apache.log4j.Logger

@NativeHookClass("android.content.res.AssetManager")
object AssetManager {
    private val logger = Logger.getLogger(AssetManager::class.java)

    @JvmStatic
    fun init(uuid:String,flag: Boolean){
        return
    }

    @JvmStatic
    fun getStringBlockCount(uuid: String):Int{
        // FIXME: 2022/3/13 getStringBlockCount return 0
        logger.warn("getStringBlockCount return 0")
        return 0
    }

    @JvmStatic
    fun addAssetPathNative(uuid: String,id:String):Int{
        // FIXME: 2022/3/13 addAssetPathNative return 0
        logger.warn("addAssetPathNative return 0")
        return 0
    }
}