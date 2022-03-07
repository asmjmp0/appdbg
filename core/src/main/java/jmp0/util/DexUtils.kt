package jmp0.util

import com.googlecode.d2j.dex.Dex2jar
import org.apache.log4j.Logger
import java.io.File
import java.util.zip.ZipFile

object DexUtils {
    val logger = Logger.getLogger(javaClass)
    /**
     * @param byteArray 源dexFile
     * @param name 需要被释放成的文件夹
     * @param out 目的文件夹
     */
    fun releaseByteArrDexClassFile(byteArray: ByteArray,name: String,out: File){
        Dex2jar.from(byteArray).eachClassData { data, className ->
            File(out,className.replace('/','_')+".class").writeBytes(data)
        }
    }

    /**
     * @param dexFile 源dexFile
     * @param out 目的文件夹
     */
    fun releaseDexClassFile(dexFile: File,out: File) =
        releaseByteArrDexClassFile(dexFile.readBytes(),dexFile.name,out)


    /**
     * @param apkFile 目标dexFile
     * @param out 目的文件夹
     */
    fun releaseApkClassFile(apkFile: File,out:File){
        ZipFile(apkFile).apply {
            entries().iterator().forEach {
                if (it.name.endsWith(".dex")){
                    logger.debug("releaseApkClassFile release ${it.name}...")
                    releaseByteArrDexClassFile(getInputStream(it).readBytes(),it.name,out)
                }
            }
        }

    }
}