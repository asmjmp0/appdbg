package jmp0.test.testapp

import android.content.Context
import android.util.Base64
import android.util.Log

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/8
 */
class TestKotlin() {

    private data class Base64Info(val data:String,val len:Int)

    private fun testxx(context: Context){
        testResources(context)
        testLopper()
    }

    private fun testBase64(): Base64Info {
        val xx by lazy { String(Base64.encode("testets".toByteArray(),0)) }
        return Base64Info(xx,xx.length)
    }

    //not implement
    public fun testResources(context: Context):String{
        return context.getResources().getString(R.string.app_name)
    }

    public fun testLopper(): Int {
        val _msgThread = MsgThread()
        Log.d("jmp0 test",_msgThread.toString())
        _msgThread.start()
        Thread.sleep(1000)
        _msgThread.sendMsg(123456789,"asm jmp0")
        _msgThread.sendMsg(238974893,"asm jmp0 mocked")
        Thread.sleep(3000)
        _msgThread.looper.quitSafely()
        Log.d("asmjmp0","lopper exit")
        return 0
    }


    fun testString(context: Context) = apply {
        ImportTest().run {
            Log.d("test",context.packageName +"=>" + testBase64())

        }
    }
}