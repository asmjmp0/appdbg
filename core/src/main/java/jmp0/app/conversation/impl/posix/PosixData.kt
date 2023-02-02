package jmp0.app.conversation.impl.posix

import jmp0.app.conversation.IAppdbgConversationData

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/2/2
 */
data class PosixData(val methodEnum: MethodEnum,val retType:Class<*>,val params:Array<out Any?>): IAppdbgConversationData