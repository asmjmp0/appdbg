package jmp0.app

import jmp0.apk.ApkFile
import java.lang.StringBuilder
import java.util.*

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/8
 */
object DbgContext {
    private val contextHashMap = hashMapOf<String,AndroidEnvironment>()

    private val methodHookHashMap = hashMapOf<String,ArrayList<MethodHookInfo>>()

    data class MethodHookInfo(val signature:String,val replace:Boolean)

    fun register(uuid: String,androidEnvironment: AndroidEnvironment)
        = synchronized(DbgContext::class.java){
        //init context map
        contextHashMap[uuid] = androidEnvironment
        //init method hook map
        methodHookHashMap[uuid] = ArrayList()

    }

    fun unRegister(uuid: String) = synchronized(DbgContext::class.java){
        contextHashMap.remove(uuid)
        methodHookHashMap.remove(uuid)
    }

    @JvmStatic
    fun getAndroidEnvironment(uuid: String) =
        contextHashMap[uuid]


    @JvmStatic
    fun getNativeCallBack(uuid: String) = synchronized(contextHashMap) {
        getAndroidEnvironment(uuid)?.methodInterceptor
    }

    fun registerMethodHook(uuid: String,signature:String,replace:Boolean) = synchronized(methodHookHashMap){
        methodHookHashMap[uuid]?.add(MethodHookInfo(signature,replace))
    }

    fun getMethodHookList(uuid: String)
        = methodHookHashMap[uuid]

    override fun toString(): String =
        StringBuilder().apply {
            append('\n')
            contextHashMap.values.forEach{
                append("$it is running\n")
            }
        }.toString()
}