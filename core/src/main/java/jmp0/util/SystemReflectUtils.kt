package jmp0.util

import java.lang.reflect.Method


object SystemReflectUtils:ReflectUtilsBase() {
    private val classLoader = ClassLoader.getSystemClassLoader()

    fun String.findSystemClass() =
        Class.forName(this,false, classLoader)

}