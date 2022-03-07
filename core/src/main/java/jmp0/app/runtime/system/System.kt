package jmp0.app.runtime.system

import org.apache.log4j.Logger

class System  {
    companion object{

        private val logger = Logger.getLogger(javaClass)

        // const const const
        const val xxClassName: String = "java.lang.System"

        @JvmStatic //the annotation is important
        fun loadLibrary(libName: String){
            logger.debug("want to load $libName...")
        }
    }
}