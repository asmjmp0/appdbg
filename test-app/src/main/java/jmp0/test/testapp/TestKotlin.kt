package jmp0.test.testapp

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
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

    public fun testLopper(){
        val t = object :Thread(){
            var _handler: Handler? = null
            override fun run() {
                super.run()
                Looper.prepare()
                 _handler = object : Handler(Looper.myLooper()!!) {
                    override fun handleMessage(msg: Message) {
                        Log.d(TAG, "获得了message")
                        super.handleMessage(msg)
                    }
                }
                Looper.loop()
            }
            fun sendMsg(what: Int, `object`: Any?) {
                val message: Message = _handler!!.obtainMessage()
                message.what = what
                message.obj = `object`
                _handler!!.sendMessage(message)
            }

        }
        t.start()
        t.sendMsg(0,1)
    }


    fun testString(context: Context) = apply {
        ImportTest().run {
            Log.d("test",context.packageName +"=>" + testBase64())

        }
    }
}