package jmp0.test.testapp

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.res.AssetManager
import android.os.Build
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.telecom.TelecomManager
import android.telephony.TelephonyManager
import android.util.Log
import java.security.Provider

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

    fun testContentResolver(){
        Log.d("asmjmp0", Settings.Secure.getString(context.contentResolver,"android_id"))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ServiceCast", "MissingPermission")
    fun testIMEI(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val tm:TelephonyManager = context.getSystemService(Service.TELEPHONY_SERVICE) as TelephonyManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val imei = tm.getImei(0)
                Log.d("asmjmp0",imei)
            }
        } else {
        }

    }

    fun testAll(){
//        testAssetManager()
//        testResources()
        testContentResolver()
        testIMEI()
    }
}