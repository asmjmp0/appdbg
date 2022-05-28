package jmp0.appdbg.external.osx.creator.java.io

import javassist.CtClass
import javassist.CtField
import jmp0.appdbg.external.ClassCreatorBase
import jmp0.appdbg.external.Common

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/5/17
 */
class PathInterceptorManagerCreator: ClassCreatorBase(getTargetClassName(PathInterceptorManagerCreator::class.java.`package`.name,"PathInterceptorManager"),CreatorType.PROJECT) {
    override fun createImpl(ctClass: CtClass): CtClass {
        return ctClass
    }
}