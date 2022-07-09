package jmp0.test.testapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.MessageQueue
import android.provider.Settings
import android.util.Log
import android.widget.TextView
import jmp0.test.testapp.net.TestNetWork
import jmp0.test.testapp.reflection.TestReflection

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TestReflection.testAll()
//        SharedPreferencesTest(this).testAll()
//        TestContext(this).testAll()
//        TestAES()
//        TestNative().testAll()
    }
}