package jmp0.app.classloader

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/11
 */
class FrameWorkClassNoFoundException(val className:String):Exception() {
    override val message: String?
        get() = "$className not found"
}