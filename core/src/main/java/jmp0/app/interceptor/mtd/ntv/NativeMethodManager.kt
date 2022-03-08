package jmp0.app.interceptor.mtd.ntv

import jmp0.app.interceptor.mtd.ntv.app.Log
import java.lang.reflect.Method

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/8
 */
object NativeMethodManager {
    private val hashMap = hashMapOf<String,Method>(
        Pair("android.util.Log.println_native(IILjava/lang/String;Ljava/lang/String;)I"
            ,Log.javaClass.getDeclaredMethod("println_native",Int::class.java,Int::class.java,String::class.java,String::class.java))
    )

    fun get(signature:String) = hashMap[signature]
}