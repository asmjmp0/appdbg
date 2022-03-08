package jmp0.app

import jmp0.util.ReflectUtilsBase

/**
 * need XAndroidDexClassLoader
 */
class AndroidReflectUtils(private val classLoader: XAndroidDexClassLoader): ReflectUtilsBase() {

    fun findAndroidClass(className:String) =
        Class.forName(className,false, classLoader)

}