package jmp0.app.interceptor.mtd

interface INativeInterceptor {
    fun nativeCalled(className:String, funcName:String, param:Array<Any>): Any?
}