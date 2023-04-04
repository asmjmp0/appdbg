package jmp0.app.interceptor.intf

import jmp0.app.AndroidEnvironment
import jmp0.app.conversation.IAppdbgConversationHandler

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/11/18
 */
interface NativeImplementInterceptor : IInterceptor, IAppdbgConversationHandler {
    fun androidEnvironmentInitFinish(androidEnvironment: AndroidEnvironment)
    fun loadLibrary(androidEnvironment: AndroidEnvironment, soName: String,fullPath:Boolean = false)
}