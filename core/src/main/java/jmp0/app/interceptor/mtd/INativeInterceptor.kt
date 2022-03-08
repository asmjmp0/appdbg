package jmp0.app.interceptor.mtd

interface INativeInterceptor {
    // TODO: 2022/3/8 add apk android env,to support program isolation.
    fun nativeCalled(className:String, funcName:String, param:Array<Any>): Any?
}