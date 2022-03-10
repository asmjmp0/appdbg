package jmp0.test.testapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.MessageQueue
import android.provider.Settings
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        TestKotlin().testLopper()
        TestNative().nativeLong
    }

    fun getStr(){
        Log.d("test",Settings.Secure.getString(this.contentResolver,"android_id"))
    }
}