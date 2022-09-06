package jmp0.util

import jmp0.app.AndroidEnvironment
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method

class ReflectionHelper(private val androidEnvironment: AndroidEnvironment,fullClassName: String) {
    private val clazz = androidEnvironment.findClass(fullClassName)
    var ins:Any? = null

    fun clazz(name: String): Class<*> = androidEnvironment.findClass(name)

    fun field(name:String): Field = clazz.getDeclaredField(name)

    /**
     * find a value and set value
     */
    fun field(name:String,value:Any?): Field = clazz.getDeclaredField(name).invoke(value)

    /**
     * get field value
     */
    operator fun Field.invoke():Any? = run{
        isAccessible = true
        get(ins)
    }

    /**
     * @param any value
     * set field value
     */
    operator fun Field.invoke(any: Any?):Field = apply {
        isAccessible = true
        set(ins,any)
    }

    fun method(name:String,vararg classes: Class<*>):Method = clazz.getDeclaredMethod(name,*classes)

    fun constructor(vararg classes: Class<*>):Constructor<*> = clazz.getDeclaredConstructor(*classes)

    /**
     * @param anys params
     * init class
     */
    operator fun Constructor<*>.invoke(vararg anys:Any): Any = newInstance(*anys).apply { ins = this }

    /**
     * new a reflectionHelper environment with the androidEnvironment
     */
    fun reflection(fullClassName: String, block: ReflectionHelper.()->Any?):Any? = ReflectionHelper(androidEnvironment,fullClassName).block()

}

fun reflection(androidEnvironment: AndroidEnvironment,fullClassName: String, block: ReflectionHelper.()->Any?):Any?  = ReflectionHelper(androidEnvironment,fullClassName).block()