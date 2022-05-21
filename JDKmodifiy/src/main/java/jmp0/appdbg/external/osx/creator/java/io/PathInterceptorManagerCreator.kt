package jmp0.appdbg.external.osx.creator.java.io

import jmp0.appdbg.external.ClassCreatorBase

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/5/17
 */
class PathInterceptorManagerCreator: ClassCreatorBase() {

    override fun create(dstDir: String) {
        val fullClassName = getTargetClassName(javaClass.`package`.name,"PathInterceptorManager")
        val ctClass = getCtClass(fullClassName,CreatorType.PROJECT)
        ctClass.also {
            it.replaceClassName(ctClass.name,fullClassName)
            it.writeFile(dstDir)
        }
    }
}