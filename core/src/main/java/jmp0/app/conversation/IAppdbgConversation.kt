package jmp0.app.conversation

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/11/17
 */
interface IAppdbgConversation<T:IAppdbgConversationData> {
    val schema:AppdbgConversationSchemaEnum
    val data:T
}