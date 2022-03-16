package jmp0.app.interceptor.intf

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/10
 */
abstract class UnidbgInterceptor():IInterceptor {
    override fun nativeCalled(
        uuid: String,
        className: String,
        funcName: String,
        signature: String,
        param: Array<out Any?>
    ): IInterceptor.ImplStatus {
        TODO("Not yet implemented")
    }
}