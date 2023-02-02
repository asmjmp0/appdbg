package jmp0.app.conversation

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/11/17
 */
enum class AppdbgConversationSchemaEnum(/*not use,just a description*/ val description:String) {
    NATIVE("native implement,native conversation."),
    SHARED_PREFERENCES("shared preferences data conversation."),
    POSIX("posix interface implement.")
}