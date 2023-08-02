package jmp0.test.testapp

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.MessageQueue
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.util.Log
import android.widget.TextView
import jmp0.test.testapp.net.TestNetWork
import jmp0.test.testapp.reflection.TestReflection

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TestReflection.testAll()
        DebugTest(111,"22").testAll(1);
        SharedPreferencesTest(this).testAll()
        TestContext(this).testAll()
        TestAES()
        TestNative().testAll()
    }
}