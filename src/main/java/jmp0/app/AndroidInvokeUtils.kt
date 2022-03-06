package jmp0.app

import org.apache.log4j.Logger
import java.lang.reflect.Constructor
import java.lang.reflect.Executable
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

class AndroidInvokeUtils(private val androidEnvironment: AndroidEnvironment) {
    private val logger = Logger.getLogger(javaClass)
    var nowClass:Class<*>? = null

    fun setNowClass(clazz: Class<*>): AndroidInvokeUtils {
        nowClass = clazz
        return this
    }

    fun invoke(e: Method,ins:Any,vararg parameter:Any): Any? {
        return try {
            e.invoke(ins,*parameter)
        }catch (e:InvocationTargetException){
            e.printStackTrace()
        }
    }

    fun getConstructor(vararg parametertype:Class<*>):Constructor<*> =
        nowClass!!.getDeclaredConstructor(*parametertype)

    fun newInstance(constructor: Constructor<*>,vararg parameter:Any): Any? =
            constructor.newInstance(*parameter)
}