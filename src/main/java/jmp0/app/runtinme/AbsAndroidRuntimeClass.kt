package jmp0.app.runtinme

import javassist.ClassPool
import javassist.CtClass

abstract class AbsAndroidRuntimeClass {
    open fun resolveClass(className:String):Class<*>?{
        // TODO: 2022/3/6 implement Android runtime class
        return ClassPool.getDefault().makeClass(className).toClass()
    }
}