package jmp0.util

import com.googlecode.d2j.dex.Dex2jar
import jmp0.conf.CommonConf
import org.apache.log4j.Logger
import java.io.File
import java.util.zip.ZipFile
import kotlin.random.Random
import kotlin.random.nextULong
import kotlin.system.exitProcess

object DexUtils {
    val logger = Logger.getLogger(javaClass)
    /**
     * @param byteArray 源dexFile
     * @param name 需要被释放成的文件夹
     * @param out 目的文件夹
     */
    fun releaseByteArrDexClassFile(byteArray: ByteArray,name: String,out: File){
        // TODO: 2022/4/1 generate app class file with debug info,which make it possible to debug class file well
        Dex2jar.from(byteArray).eachClassData { data, className ->
            //first is the path where the class should be store
            //second is the name of class file
            className.split('/').let {
                Pair(it.subList(0,it.size-1).let { pathList->
                    StringBuilder().apply {
                        pathList.forEach { pathItem->
                            append("$pathItem${File.separator}")
                        }
                    }.toString()
                },it[it.size-1])
            }.also {
                File(File(out,it.first).apply {if (!exists()) mkdirs()},it.second+".class").writeBytes(data)
            }
        }
        ZipUtility.zip(out.path,File(File("${CommonConf.workDir}${File.separator}libs${File.separator}${CommonConf.appJarDir}").apply {
            if (!exists()) mkdirs()
            else {
                deleteRecursively()
                mkdirs()
            }},Random.nextULong().toString()+".jar").canonicalPath)
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