package jmp0.app.conversation.impl.posix

import jmp0.app.conversation.AppdbgConversationSchemaEnum
import jmp0.app.conversation.IAppdbgConversation

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/2/2
 */
class PosixConversation(override val data: PosixData,
                        override val schema: AppdbgConversationSchemaEnum = AppdbgConversationSchemaEnum.POSIX
) :IAppdbgConversation<PosixData>