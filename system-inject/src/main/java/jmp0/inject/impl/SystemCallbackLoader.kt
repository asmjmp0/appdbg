package jmp0.inject.impl

import javassist.ClassPool
import javassist.CtClass
import jmp0.inject.intf.IInject

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/7
 */
class SystemCallbackLoader:IInject {
    override fun generateClass(): IInject.ClassInfo =
        ClassPool.getDefault().getCtClass("jmp0.inject.SystemCallback").toBytecode().run {
            IInject.ClassInfo(this,"jmp0.inject.SystemCallback")
        }
}