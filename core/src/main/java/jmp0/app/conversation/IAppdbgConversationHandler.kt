package jmp0.app.conversation

import jmp0.app.AndroidEnvironment
import jmp0.app.interceptor.intf.IInterceptor

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/11/18
 */
interface IAppdbgConversationHandler {
    fun appdbgConversationHandle(androidEnvironment: AndroidEnvironment, conversation: IAppdbgConversation<*>):IInterceptor.ImplStatus
}