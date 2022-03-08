package jmp0.util

import java.lang.reflect.Field
import java.lang.reflect.Method

abstract class ReflectUtilsBase {

    private fun Method.setAccessibleEx() = apply {
        isAccessible = true
    }

    private fun Field.setAccessibleEx() = apply {
        isAccessible = true
    }

    fun Method.invokeEx(ins:Any,vararg parameter:Any):Any? =
        setAccessibleEx().invoke(ins,*parameter)

    fun Field.getEx(ins: Any) =
        setAccessibleEx().get(ins)
}