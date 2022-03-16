package jmp0.app.mock.annotations

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/16
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class ParamType(val before:String,val type:String)
