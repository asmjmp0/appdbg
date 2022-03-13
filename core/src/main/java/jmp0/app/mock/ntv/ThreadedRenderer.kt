package jmp0.app.mock.ntv

import jmp0.app.mock.NativeHookClass
import org.apache.log4j.Logger

@NativeHookClass("android.view.ThreadedRenderer")
object ThreadedRenderer {

    private val logger = Logger.getLogger(ThreadedRenderer::class.java)

    @JvmStatic
    fun setupShadersDiskCache(uuid:String,path:String){
        logger.debug("setupShadersDiskCache $path called")
    }
}