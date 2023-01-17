package jmp0.app.conversation.impl.sp

import jmp0.app.conversation.IAppdbgConversationData

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/1/17
 */
data class SharedPreferencesData(val methodName:String,val retType:Class<*>,val params:Array<out Any?>):IAppdbgConversationData