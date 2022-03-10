package jmp0.app.interceptor.intf

interface IInterceptor {
    data class ImplStatus(val implemented:Boolean,val result:Any?)

    // TODO: 2022/3/10 给native函数增加测试用例，保证返回值正确
    fun nativeCalled(className:String, funcName:String, signature:String,param:Array<out Any?>): ImplStatus
    fun methodCalled(className:String, funcName:String, signature: String,param:Array<out Any?>): Any?
}