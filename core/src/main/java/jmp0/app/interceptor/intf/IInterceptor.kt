package jmp0.app.interceptor.intf

interface IInterceptor {
    data class ImplStatus(val implemented:Boolean,val result:Any?)
    fun nativeCalled(uuid:String,className:String, funcName:String, signature:String,param:Array<Any?>): ImplStatus
    fun methodCalled(uuid:String,className:String,instance:Any? ,funcName:String, signature: String,param:Array<Any?>): Any?
    fun ioResolver(path: String):String?
}