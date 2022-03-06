package jmp0.app.interceptor

interface INativeInterceptor {
    fun nativeCalled(className:String, funcName:String, param:Array<Any>): Any?
}