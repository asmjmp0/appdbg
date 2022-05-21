package jmp0.appdbg.external

import javassist.ClassPool
import javassist.CtClass

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/5/17
 */
abstract class ClassCreatorBase {
    enum class CreatorType{
        PROJECT,
        RUNTIME
    }
    /**
     * @param fullClassName
     * @param type
     * 0 => get from this project below the Common.rootPackageName
     * 1 = > get from runtime
     */
    protected fun getCtClass(fullClassName:String,type:CreatorType): CtClass =
        when(type){
            CreatorType.PROJECT->{
                ClassPool.getDefault().getCtClass("${Common.rootPackageName}.${Common.systemNowPackage}.${fullClassName}")
            }
            CreatorType.RUNTIME->{
                ClassPool.getDefault().getCtClass(fullClassName)
            }
        }

    protected fun getTargetClassName(packageName:String,className:String):String{
        val packageName = packageName.replace("${Common.rootPackageName}.${Common.systemNowPackage}.${Common.creatorName}.","")
        return "$packageName.$className"
    }
    abstract fun create(dstDir:String)
}