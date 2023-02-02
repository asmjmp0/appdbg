package jmp0.app.conversation.impl.sp

import jmp0.app.conversation.AppdbgConversationSchemaEnum
import jmp0.app.conversation.IAppdbgConversation

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/1/17
 */
class SharedPreferencesConversation(
    override val data: SharedPreferencesData,
    override val schema: AppdbgConversationSchemaEnum = AppdbgConversationSchemaEnum.SHARED_PREFERENCES
): IAppdbgConversation<SharedPreferencesData>