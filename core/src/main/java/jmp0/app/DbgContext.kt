package jmp0.app

import java.lang.StringBuilder
import java.util.*

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/8
 */
object DbgContext {
    private val hashMap = hashMapOf<String,AndroidEnvironment>()

    fun register(uuid: String,androidEnvironment: AndroidEnvironment) = synchronized(hashMap){
        hashMap.put(uuid,androidEnvironment)
    }

    fun unRegister(uuid: String) = synchronized(hashMap){
        hashMap.remove(uuid)
    }

    @JvmStatic
    fun getNativeCallBack(uuid: String) = synchronized(hashMap) {
        hashMap[uuid]!!.nativeInterceptor
    }

    override fun toString(): String = synchronized(hashMap) {
        StringBuilder().apply {
            append('\n')
            hashMap.values.forEach{
                append("$it is running\n")
            }
        }.toString()
    }
}