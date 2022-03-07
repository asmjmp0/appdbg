package jmp0.inject.intf

import javassist.ClassPool
import javassist.CtClass
import javassist.Modifier

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/7
 */
abstract class AbsGetClass(private val className:String) {

    protected fun checkNativeFlag(modifiers:Int ) =
        (modifiers and Modifier.NATIVE) == Modifier.NATIVE

    protected fun eraseNativeFlag(modifiers: Int) =
        (modifiers and (Modifier.NATIVE.inv()))
    
    protected fun getCtClass(): CtClass {
        return ClassPool.getDefault().getCtClass(className)
    }
}