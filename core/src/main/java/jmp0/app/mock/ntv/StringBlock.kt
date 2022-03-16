package jmp0.app.mock.ntv

import brut.androlib.res.data.ResID
import brut.androlib.res.data.value.ResStringValue
import brut.androlib.res.decoder.ARSCDecoder
import jmp0.app.DbgContext
import jmp0.app.mock.annotations.NativeHookClass
import java.util.*

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/16
 */
@NativeHookClass("android.content.res.StringBlock")
object StringBlock {

    @JvmStatic
    fun nativeGetSize(uuid: String,id:Long):Int{
        return 10000
    }

    @JvmStatic
    fun nativeGetString(uuid: String,id:Long,idx:Int):String{
        val env = DbgContext.getAndroidEnvironment(uuid)!!
        val apkFile = env.apkFile
        val a = java.util.zip.ZipFile(apkFile.copyApkFile)
        a.entries().iterator().forEach {
            if (it.name == "resources.arsc") {
                val ins = a.getInputStream(it)
                val decoder = ARSCDecoder.decode(ins, true, true)
                val a = decoder.onePackage.listValuesFiles().forEach {
                    it.listResources().forEach {
                        if(it.value is ResStringValue){
                            if((it.value as ResStringValue).rawIntValue == idx) {
                                return (it.value as ResStringValue).encodeAsResXmlItemValue()
                            }
                        }
                    }
                }

            }
        }
        throw Exception("not implement")
    }

    @JvmStatic
    fun nativeGetStyle(uuid: String,id:Long,idx:Int):IntArray?{
        return null
    }
}