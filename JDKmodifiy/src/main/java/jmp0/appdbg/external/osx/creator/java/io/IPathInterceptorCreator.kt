package jmp0.appdbg.external.osx.creator.java.io

import javassist.CtClass
import javassist.CtField
import jmp0.appdbg.external.ClassCreatorBase
import jmp0.appdbg.external.Common

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/5/17
 */
class IPathInterceptorCreator: ClassCreatorBase(IPathInterceptorCreator::class.java.name,getTargetClassName(IPathInterceptorCreator::class.java.`package`.name,"IPathInterceptor"),CreatorType.PROJECT) {
    override fun createImpl(ctClass: CtClass): CtClass {
        return ctClass
    }

}