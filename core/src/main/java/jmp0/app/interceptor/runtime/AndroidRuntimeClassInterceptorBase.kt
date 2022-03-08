package jmp0.app.interceptor.runtime

import javassist.ClassPool
import javassist.CtClass
import jmp0.app.AndroidEnvironment
import jmp0.app.XAndroidDexClassLoader
import jmp0.app.interceptor.mtd.ClassNativeInterceptor
import jmp0.app.interceptor.mtd.HookMethodInterceptor
import org.apache.log4j.Logger

abstract class AndroidRuntimeClassInterceptorBase {

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

     open fun afterFindClassFile(androidEnvironment: AndroidEnvironment,ctClass: CtClass):CtClass{
        //after  xclassloader find the class file,you can modify the class

        //erase native function acess flag and insert callback
        var pass =  ClassNativeInterceptor(androidEnvironment,ctClass).doChange()

         //make hook java method possible
         pass = HookMethodInterceptor(androidEnvironment,ctClass).doChange()

        return pass
    }

     open fun beforeResolveClass(androidEnvironment: AndroidEnvironment,className:String,classLoader: XAndroidDexClassLoader):Class<*>?{
        //before xclassloader find the class file,you can replace or implement the class
        if (className == "androidx.appcompat.app.AppCompatActivity"){
            //use my class
            return loadToClass("androidx.appcompat.app.AppCompatActivity","jmp0.app.runtime.android.AppCompatActivity",classLoader)
        }
        return null
    }

}