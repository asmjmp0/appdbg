package jmp0.test.testapp

import android.content.Context
import android.provider.Settings
import android.util.Log

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/8
 */
class TestKotlin() {
    fun testString() = apply {
        ImportTest().run {
            Log.d("test","which is the logger")
        }
    }
}