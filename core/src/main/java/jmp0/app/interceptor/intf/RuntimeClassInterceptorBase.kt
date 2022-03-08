package jmp0.app.interceptor.intf

import javassist.CtClass
import javassist.Modifier
import jmp0.app.AndroidEnvironment

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/7
 * use to give base func and a interface to  pass chain
 */
abstract class RuntimeClassInterceptorBase(private val androidEnvironment: AndroidEnvironment,private val ctClass: CtClass) {

    protected fun checkNativeFlag(modifiers:Int ) =
        (modifiers and Modifier.NATIVE) == Modifier.NATIVE

    protected fun eraseNativeFlag(modifiers: Int) =
        (modifiers and (Modifier.NATIVE.inv()))

    protected fun replaceType(type:String):String =
         when(type){
             "byte"->"Byte"
             "short"->"Short"
             "int"->"Integer"
             "long"->"Long"
             "float"->"Float"
             "double"->"Double"
             "char"->"Char"
             "boolean"->"Boolean"
             "void"->"Void"
             else->type
         }

    abstract fun doChange():CtClass
}