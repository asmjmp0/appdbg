package jmp0.util

import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method

class ReflectionHelper(private val classLoader: ClassLoader,fullClassName: String) {
    private val clazz = Class.forName(fullClassName,false,classLoader)
    var ins:Any? = null

    fun clazz(name: String): Class<*> = Class.forName(name,false,classLoader)

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

    fun method(name:String,vararg classes: Class<*>):Method = clazz.getDeclaredMethod(name,*classes).apply { isAccessible = true }

    fun constructor(vararg classes: Class<*>):Constructor<*> = clazz.getDeclaredConstructor(*classes)

    /**
     * @param anys params
     * init class
     */
    operator fun Constructor<*>.invoke(vararg anys:Any): Any = newInstance(*anys).apply { ins = this }

    /**
     * new a reflectionHelper environment with the androidEnvironment
     */
    fun reflection(fullClassName: String, block: ReflectionHelper.()->Any?):Any? = ReflectionHelper(classLoader,fullClassName).block()

}

fun reflection(classLoader: ClassLoader,fullClassName: String, block: ReflectionHelper.()->Any?):Any?  = ReflectionHelper(classLoader,fullClassName).block()