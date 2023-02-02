package jmp0.app.conversation.impl.ntv

import jmp0.app.conversation.AppdbgConversationSchemaEnum
import jmp0.app.conversation.IAppdbgConversation

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/11/17
 */
class NativeConversation(override val data: NativeData,
                         override val schema: AppdbgConversationSchemaEnum = AppdbgConversationSchemaEnum.NATIVE,
):IAppdbgConversation<NativeData>