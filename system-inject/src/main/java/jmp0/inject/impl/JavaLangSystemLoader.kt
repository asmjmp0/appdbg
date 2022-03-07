package jmp0.inject.impl

import jmp0.inject.intf.AbsGetClass
import jmp0.inject.intf.IInject

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/7
 */
class JavaLangSystemLoader(private val className:String = "java.lang.System"): AbsGetClass(className), IInject {


    override fun generateClass(): IInject.ClassInfo =
        getCtClass().apply {
            declaredMethods.forEach {
            }
        }.toBytecode().run {
                IInject.ClassInfo(this, className)
            }
}