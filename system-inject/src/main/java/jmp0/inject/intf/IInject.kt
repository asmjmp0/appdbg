package jmp0.inject.intf

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/7
 */
interface IInject {
    data class ClassInfo(val data:ByteArray,val name:String)

    fun generateClass(): ClassInfo
}