package jmp0.app

import java.lang.StringBuilder
import java.util.*

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/8
 */
object DbgContext {
    private val contextHashMap = hashMapOf<String,AndroidEnvironment>()

    data class MethodHookInfo(val signature:String,val replace:Boolean)

    private val methodHookHashMap = hashMapOf<String,ArrayList<MethodHookInfo>>()

    fun register(uuid: String,androidEnvironment: AndroidEnvironment){
        //init context map
        contextHashMap[uuid] = androidEnvironment
        //init method hook map
        methodHookHashMap[uuid] = ArrayList()

    }

    fun unRegister(uuid: String) = synchronized(contextHashMap){
        contextHashMap.remove(uuid)
    }

    @JvmStatic
    fun getAndroidEnvironment(uuid: String) = synchronized(contextHashMap){
        contextHashMap[uuid]
    }

    @JvmStatic
    fun getNativeCallBack(uuid: String) = synchronized(contextHashMap) {
        getAndroidEnvironment(uuid)?.nativeInterceptor
    }

    fun registerMethodHook(uuid: String,signature:String,replace:Boolean) = synchronized(methodHookHashMap){
        methodHookHashMap[uuid]?.add(MethodHookInfo(signature,replace))
    }

    fun getMethodHookList(uuid: String)
        = methodHookHashMap[uuid]

    override fun toString(): String = synchronized(contextHashMap) {
        StringBuilder().apply {
            append('\n')
            contextHashMap.values.forEach{
                append("$it is running\n")
            }
        }.toString()
    }
}