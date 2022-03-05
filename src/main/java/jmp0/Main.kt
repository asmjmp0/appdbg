package jmp0

import jmp0.app.AndroidEnvironment
import java.io.File

class Main {
    companion object{
        @JvmStatic
        fun main(args:Array<String>){
            val ret = AndroidEnvironment().loader.loadClass(File("test_data/classes/com_example_myapplication_TestKotlin.class"))
            val ins = ret?.getDeclaredConstructor(String::class.java)?.newInstance("11111111")
            val res = ret?.getDeclaredMethod("getStr")?.invoke(ins)
            println(res)

        }
    }
}