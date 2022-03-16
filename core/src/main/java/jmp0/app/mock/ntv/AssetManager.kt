package jmp0.app.mock.ntv

import android.util.TypedValue
import brut.androlib.res.data.ResID
import brut.androlib.res.data.value.ResIntBasedValue
import brut.androlib.res.data.value.ResStringValue
import brut.androlib.res.decoder.ARSCDecoder
import jmp0.app.DbgContext
import jmp0.app.mock.annotations.NativeHookClass
import jmp0.app.mock.annotations.ParamType
import org.apache.log4j.Logger
import java.io.File
import kotlin.collections.HashMap

@NativeHookClass("android.content.res.AssetManager")
object AssetManager {
    private val logger = Logger.getLogger(AssetManager::class.java)
    private val random = java.util.Random()
    private data class FileDes(val file: File,val complete:Long)
    private val handleMap = HashMap<Long,FileDes>()


    @JvmStatic
    fun init(uuid:String,flag: Boolean){
        return
    }

    @JvmStatic
    fun getStringBlockCount(uuid: String):Int{
        logger.warn("getStringBlockCount return 1")
        return 1
    }

    @JvmStatic
    fun getNativeStringBlock(uuid: String,id:Int):Long{
        return 10000;
    }

    @JvmStatic
    fun addAssetPathNative(uuid: String,id:String):Int{
        logger.warn("addAssetPathNative return 1")
        return 1
    }

    @JvmStatic
    fun getAssetLength(uuid: String,id: Long):Long{
        val f = handleMap[id]!!
        return f.file.length()
    }

    @JvmStatic
    fun getAssetRemainingLength(uuid: String,id:Long):Long{
        // FIXME: 2022/3/16 over xxx
        val f = handleMap[id]!!
        return f.file.length()
    }

    @JvmStatic
    fun readAsset(uuid: String,id:Long,byteArray: ByteArray,a: Int,b: Int):Int{
        val fd = handleMap[id]!!
        val content = fd.file.readBytes()
        if (fd.complete>=content.size) {
            handleMap.remove(id)
            return -1
        }
        val needRead = content.size - fd.complete
        var realSize = 0
        if (needRead < b){
            System.arraycopy(content,fd.complete.toInt(),byteArray,0,needRead.toInt())
            handleMap[id] = FileDes(fd.file,fd.complete + needRead)
            realSize = needRead.toInt()
        }else{
            System.arraycopy(content,fd.complete.toInt(),byteArray,0,b)
            handleMap[id] = FileDes(fd.file,fd.complete + b)
            realSize = b
        }
        return realSize
    }


    @JvmStatic
    fun openAsset(uuid: String,path:String,access:Int):Long{
        val randomID = random.nextLong()
        val env = DbgContext.getAndroidEnvironment(uuid)!!
        val dir = env.apkFile.assetsDir
        handleMap[randomID] = FileDes(File(dir,path),0)
        return randomID
    }

    @JvmStatic
    fun setConfiguration(uuid: String,a:Int,b:Int,c:String,d:Int,e:Int,f:Int
                         ,g:Int,h:Int,i:Int,j:Int,k:Int,l:Int,m:Int,n:Int,o:Int,p:Int,q:Int){
        logger.debug("setConfiguration")
    }

    @JvmStatic
    fun loadResourceValue(uuid: String, i:Int, s:Short, @ParamType("S","Landroid/util/TypedValue;") type: Any, b:Boolean):Int{
        val env = DbgContext.getAndroidEnvironment(uuid)!!
        val apkFile = env.apkFile
        val a = java.util.zip.ZipFile(apkFile.copyApkFile)
        a.entries().iterator().forEach {
            if (it.name == "resources.arsc"){
                val ins = a.getInputStream(it)
                val decoder = ARSCDecoder.decode(ins,true,true)
                val b = decoder.packages[0].getResSpec(ResID(i))
                val c = b.defaultResource.value
                if (c is ResStringValue){
                    val clazz = env.findClass("android.util.TypedValue")
                    val typef = clazz.getDeclaredField("type")
                    val dataf = clazz.getDeclaredField("data")
                    typef.setInt(type,TypedValue.TYPE_STRING)
                    dataf.setInt(type,c.rawIntValue)
                }
            }
        }
        return 0
    }
}