package jmp0.inject

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/7
 */
class GenerateMain {
    companion object{
        /**
         * see jmp0.inject.impl.JavaLangSystem
         */
        @JvmStatic
        fun main(args:Array<String>){
            InvokeImplementation("system-inject/src/main/java/jmp0/inject/impl","jmp0.inject.impl").invoke()
        }
    }
}