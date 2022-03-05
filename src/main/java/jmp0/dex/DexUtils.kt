package jmp0.dex

import com.googlecode.d2j.dex.Dex2jar
import java.io.File
import java.util.zip.ZipFile

object DexUtils {

    /**
     * @param byteArray 源dexFile
     * @param name 需要被释放成的文件夹
     * @param out 目的文件夹
     */
    fun releaseByteArrDexClassFile(byteArray: ByteArray,name: String,out: File){
        val dir = File(out,name).apply { mkdir() }
        Dex2jar.from(byteArray).eachClassData { data, className ->
            File(dir,className.replace('/','_')+".class").writeBytes(data)
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
            entries().asIterator().forEach {
                if (it.name.endsWith(".dex")){
                    println("releaseApkClassFile release ${it.name}...")
                    releaseByteArrDexClassFile(getInputStream(it).readBytes(),it.name,out)
                }
            }
        }

    }
}