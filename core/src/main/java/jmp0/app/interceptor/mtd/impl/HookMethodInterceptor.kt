package jmp0.app.interceptor.mtd.impl

import javassist.CtClass
import jmp0.app.AndroidEnvironment
import jmp0.app.interceptor.intf.RuntimeClassInterceptorBase

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/7
 */
class HookMethodInterceptor(private val androidEnvironment: AndroidEnvironment,private val ctClass: CtClass)
    : RuntimeClassInterceptorBase(androidEnvironment,ctClass) {

    override fun doChange(): CtClass {
        // TODO: 2022/3/7 支持普通函数的hook 用INativeInterceptor接口实现
        return ctClass
    }
}