package jmp0.test.testapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.MessageQueue
import android.provider.Settings
import android.util.Log
import jmp0.test.testapp.net.TestNetWork

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val b = this.packageManager.getInstrumentationInfo(,0)
//        Log.d("asmjmp0",java.lang.System.getenv("ANDROID_DATA"))
//        Log.d("asmjmp0",java.lang.System.getenv("ANDROID_STORAGE"))
////        Log.d("asmjmp0",java.lang.System.getenv("OEM_ROOT"))
//        Log.d("asmjmp0","111"+java.lang.System.getenv("VENDOR_ROOT"))
//        TestNetWork().test()
    }

    fun getStr(){
        Log.d("test",Settings.Secure.getString(this.contentResolver,"android_id"))
    }
}