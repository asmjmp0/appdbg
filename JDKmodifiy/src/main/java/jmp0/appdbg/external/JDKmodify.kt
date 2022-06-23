package jmp0.appdbg.external

import jmp0.appdbg.external.impl.GeneratorDefault
import jmp0.appdbg.external.impl.ioredirect.IORedirectGenerator
import java.io.File
import java.util.jar.JarFile
import java.util.zip.ZipFile

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/5/17
 */
class JDKmodify {
    companion object{

        private fun generateJDKFile(){
            GeneratorDefault.deleteOldClassDir()
            //IO Redirect
            IORedirectGenerator.generate()
            // TODO: 2022/6/23 reflection class support

            GeneratorDefault.writeToRTJAR()
        }

        @JvmStatic
        fun main(args:Array<String>){
            generateJDKFile()
        }
    }
}