package jmp0.test.testapp

import android.content.Context
import android.content.res.AssetManager
import android.util.Log

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/16
 */
class TestContext(private val context: Context) {

    fun testAssetManager(){
        Log.d("asmjmp0",String(context.assets.open("hello").readBytes()))
    }

    fun testResources(){
        Log.d("asmjmp0",context.resources.getString(R.string.app_name))
    }

    fun testAll(){
//        testAssetManager()
        testResources()
    }
}