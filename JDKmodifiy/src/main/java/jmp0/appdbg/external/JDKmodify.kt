package jmp0.appdbg.external

import jmp0.appdbg.external.impl.ioredirect.IORedirectGenerator

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/5/17
 */
class JDKmodify {
    companion object{

        private fun generateJDKFile(){
            //IO Redirect
            IORedirectGenerator.generate()
            IORedirectGenerator.writeToRTJAR()
        }

        @JvmStatic
        fun main(args:Array<String>){
            generateJDKFile()
        }
    }
}