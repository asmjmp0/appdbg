package jmp0.test.testapp.net

import android.util.Log
import okhttp3.*
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/11
 */
class TestNetWork {

    fun getHtml(path: String?): String? {
        val url = URL(path)
        val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
        conn.setConnectTimeout(5000)
        conn.requestMethod = "GET"
        if (conn.responseCode == 200) {
            val `in`: InputStream = conn.getInputStream()
            val data: ByteArray = StreamTool.read(`in`)!!
            return String(data)
        }
        return null
    }

    private fun getAsyncOkHttp() {
        val request = Request.Builder()
            .url("https://www.baidu.com")
            .build()

        val client = OkHttpClient.Builder()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }
            //请求成功
            override fun onResponse(call: Call, response: Response) {
                Log.d("asmjmp0", response.toString())

            }
        })
    }

    private fun get(): String? {
        val url = "https://wwww.baidu.com"
        val okHttpClient = OkHttpClient()
        val request: Request = Request.Builder()
            .url(url)
            .build()
        val call = okHttpClient.newCall(request)
        return call.execute().body().string()
    }
    fun test(): String? {
//            Log.d("asmjmp0", getAsyncOkHttp().toString())
        return get()
//        System.exit(0)
//            Log.d("asmjmp0",getHtml("http://www.baidu.com")!!)
    }
}