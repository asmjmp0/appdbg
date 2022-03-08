package jmp0.app.interceptor.intf

interface INativeInterceptor {
    data class ImplStatus(val implemented:Boolean,val result:Any?)
    // TODO: 2022/3/8 add apk android env,to support program isolation.
    fun nativeCalled(className:String, funcName:String, param:Array<out Any?>): ImplStatus
}