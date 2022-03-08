package jmp0.app.clazz

import javassist.ClassPool
import javassist.CtClass
import jmp0.app.AndroidEnvironment
import jmp0.app.XAndroidDexClassLoader
import jmp0.app.interceptor.mtd.impl.ClassNativeInterceptor
import jmp0.app.interceptor.mtd.impl.HookMethodInterceptor
import org.apache.log4j.Logger

/**
 * use to intercept the class before or after class loaded
 */
abstract class ClassLoadedCallbackBase {

     fun loadToClass(what:String, replace:String, classLoader: XAndroidDexClassLoader):Class<*>{
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
     open fun afterResolveClass(androidEnvironment: AndroidEnvironment,ctClass: CtClass):CtClass{
         // TODO: 2022/3/8 use reflection to invoke form jmp0.app.interceptor.mtd.impl
        //erase native function acess flag and insert callback
        var pass =  ClassNativeInterceptor(androidEnvironment,ctClass).doChange()

         //make hook java method possible
         pass = HookMethodInterceptor(androidEnvironment,pass).doChange()

        return pass
    }

    /**
     * before xclassloader find the class file,you can replace or implement the class
     * if you implement the class and return
     * not call afterResolveClass
     */
     open fun beforeResolveClass(androidEnvironment: AndroidEnvironment,className:String,classLoader: XAndroidDexClassLoader):Class<*>?{
        if (className == "androidx.appcompat.app.AppCompatActivity"){
            //use my class
            return loadToClass("androidx.appcompat.app.AppCompatActivity","jmp0.app.clazz.android.AppCompatActivity",classLoader)
        }
        if (className == "android.support.v7.app.AppCompatActivity"){
            return  loadToClass("android.support.v7.app.AppCompatActivity","jmp0.app.clazz.android.AppCompatActivity",classLoader)
        }
        if (className == "android.support.v7.app.AppCompatCallback"){
            return  loadToClass("android.support.v7.app.AppCompatCallback","jmp0.app.clazz.android.AppCompatActivity",classLoader)
        }
        return null
    }

}