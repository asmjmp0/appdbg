package jmp0.test.testapp

import android.util.Log
import java.io.File

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/5/16
 */
class FileTest {
    fun getMaps(){
        val ls = File("/proc/self/maps").readLines()
        ls.forEach {
            Log.d("asmjmp0",it)
        }
    }
    fun testAll(){
        getMaps()
    }
}