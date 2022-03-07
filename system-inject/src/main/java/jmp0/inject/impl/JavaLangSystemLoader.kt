package jmp0.inject.impl

import jmp0.inject.intf.AbsGetClass
import jmp0.inject.intf.IInject
import javassist.CtMethod
import javassist.Modifier
import javassist.bytecode.stackmap.TypeData

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/7
 */
class JavaLangSystemLoader(private val className:String = "java.lang.System"): AbsGetClass(className), IInject {


    override fun generateClass(): IInject.ClassInfo =
        getCtClass().apply {
            declaredMethods.forEach {
                if (it.name == "loadLibrary"){
                    it.insertBefore("""{
                        if($1=="myapplication"){
                        System.out.println("returned!");
                        return;
                        }
                       try{
                       Class.forName("jmp0.inject.SystemCallBack");
                       }catch(Throwable e){
                        System.out.println(e.toString());
                       }
                    }
                    """.trimIndent())
                }
            }
        }.run {
            toBytecode().run {
                IInject.ClassInfo(this, className)
            }
        }
}