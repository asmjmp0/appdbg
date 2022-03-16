package jmp0.app.mock.method

import jmp0.app.mock.annotations.MethodHookClass

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/16
 */
@MethodHookClass("android.content.res.Resources")
object Resources {

    @JvmStatic
    fun adjustLanguageTag(uuid: String,id:String):String{
        return id.split('-')[0]
    }
}