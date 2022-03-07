package jmp0.app.interceptor.runtime

import javassist.CtClass
import javassist.Modifier

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/7
 */
abstract class RuntimeClassInterceptorBase(private val ctClass: CtClass) {

    protected fun checkNativeFlag(modifiers:Int ) =
        (modifiers and Modifier.NATIVE) == Modifier.NATIVE

    protected fun eraseNativeFlag(modifiers: Int) =
        (modifiers and (Modifier.NATIVE.inv()))

    abstract fun doChange():CtClass
}