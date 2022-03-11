package jmp0.test.testapp.net

import java.io.ByteArrayOutputStream
import java.io.InputStream

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/11
 */
class StreamTool {
    //从流中读取数据
    companion object{
        fun read(inStream: InputStream): ByteArray? {
            val outStream = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var len = 0
            while (inStream.read(buffer).also { len = it } != -1) {
                outStream.write(buffer, 0, len)
            }
            inStream.close()
            return outStream.toByteArray()
        }
    }
}