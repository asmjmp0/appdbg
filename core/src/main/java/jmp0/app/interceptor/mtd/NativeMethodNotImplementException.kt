package jmp0.app.interceptor.mtd

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/8
 */
class NativeMethodNotImplementException(sig:String):Exception("$sig not implement")