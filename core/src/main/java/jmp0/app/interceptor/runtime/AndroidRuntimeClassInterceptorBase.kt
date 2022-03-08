package jmp0.app.interceptor.runtime

import javassist.ClassPool
import javassist.CtClass
import jmp0.app.XAndroidDexClassLoader
import jmp0.app.interceptor.mtd.ClassNativeInterceptor
import jmp0.app.interceptor.mtd.HookMethodInterceptor
import org.apache.log4j.Logger

abstract class AndroidRuntimeClassInterceptorBase {

     fun loadToClass(what:String, replace:String, classLoader: XAndroidDexClassLoader):Class<*>
        = ClassPool.getDefault().getCtClass(replace).apply {
            replaceClassName(replace,what)
        }.toBytecode().run { classLoader.xDefineClass(null,this,0,size) }

    private val logger = Logger.getLogger(javaClass)

    // TODO: 2022/3/8 add apk android env,to support program isolation.
     open fun afterFindClassFile(ctClass: CtClass):CtClass{
        //after  xclassloader find the class file,you can modify the class

        //erase native function acess flag and insert callback
        var pass =  ClassNativeInterceptor(ctClass).doChange()

         //make hook java method possible
         pass = HookMethodInterceptor(ctClass).doChange()

        return pass
    }

    // TODO: 2022/3/8 add apk android env,to support program isolation.
     open fun beforeResolveClass(className:String,classLoader: XAndroidDexClassLoader):Class<*>?{
        //before xclassloader find the class file,you can replace or implement the class
        if (className == "androidx.appcompat.app.AppCompatActivity"){
            //use my class
            return loadToClass("androidx.appcompat.app.AppCompatActivity","jmp0.app.runtime.android.AppCompatActivity",classLoader)
        }
        return null
    }

}