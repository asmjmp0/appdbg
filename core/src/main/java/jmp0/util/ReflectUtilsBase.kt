package jmp0.util

import java.lang.reflect.Method

abstract class ReflectUtilsBase {
    fun Method.setAccessibleEx() = apply {
        isAccessible = true
    }
}