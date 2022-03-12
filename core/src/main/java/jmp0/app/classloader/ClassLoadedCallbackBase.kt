package jmp0.app.classloader

import javassist.ClassPool
import javassist.CtClass
import jmp0.app.AndroidEnvironment
import jmp0.app.interceptor.mtd.impl.NativeMethodInterceptor
import jmp0.app.interceptor.mtd.impl.HookMethodInterceptor
import org.apache.log4j.Logger

/**
 * use to intercept the class before or after class loaded
 */
abstract class ClassLoadedCallbackBase {

      fun loadToClass(what:String, replace:String, classLoader: XAndroidClassLoader):Class<*>{
         val clazz = ClassPool.getDefault().getCtClass(replace)
         val ba = clazz.apply {
             replaceClassName(replace,what)
         }.toBytecode()
         val ret = classLoader.xDefineClass(null,ba,0,ba.size)
         //xxxxx
         clazz.defrost()
         return ret
     }


    private val logger = Logger.getLogger(javaClass)

    /**
     * after xclassloader find the class file,you can modify the class
     * such as insert interceptor or just modify some static field
     */
     fun afterResolveClass(androidEnvironment: AndroidEnvironment, ctClass: CtClass):CtClass{
         //make hook java method possible,native function can not be hooked
         var pass = HookMethodInterceptor(androidEnvironment,ctClass).doChange()

         //erase native function access flag and insert callback
         pass =  NativeMethodInterceptor(androidEnvironment,pass).doChange()

         pass = afterResolveClassImpl(androidEnvironment,pass)
         return pass
     }

    abstract fun afterResolveClassImpl(androidEnvironment: AndroidEnvironment, ctClass: CtClass):CtClass



    /**
     * before xclassloader find the class file,you can replace or implement the class
     * if you implement the class and return
     * not call afterResolveClass
     */
    fun beforeResolveClass(androidEnvironment: AndroidEnvironment, className:String, classLoader: XAndroidClassLoader):Class<*>?{
        return beforeResolveClassImpl(androidEnvironment,className,classLoader)
    }

    abstract fun beforeResolveClassImpl(androidEnvironment: AndroidEnvironment, className:String, classLoader: XAndroidClassLoader):Class<*>?

}