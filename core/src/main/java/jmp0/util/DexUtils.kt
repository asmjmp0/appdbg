package jmp0.util

import com.googlecode.d2j.dex.Dex2jar
import jmp0.apk.ApkFile
import jmp0.conf.CommonConf
import jmp0.decompiler.AppdbgDecompiler
import net.lingala.zip4j.model.ExcludeFileFilter
import org.apache.log4j.Logger
import java.io.File
import java.io.FileOutputStream
import java.util.jar.JarFile
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream
import kotlin.io.path.Path
import kotlin.random.Random
import kotlin.random.nextULong
import kotlin.system.exitProcess

object DexUtils {
    val logger = Logger.getLogger(javaClass)

    private fun pathFilter(path:String):Boolean =
        //avoid JetBrains kotlin plugin inner error
         path.startsWith("org/intellij/lang/annotations") || path.startsWith("org/jetbrains/annotations")

    /**
     * @param byteArray 源dexFile
     * @param name 需要被释放成的文件夹
     * @param out 目的文件夹
     */
    fun releaseByteArrDexClassFile(byteArray: ByteArray,name: String,out: File,apkFile: ApkFile){
        val classJar = File(apkFile.classesDir,name.split('.')[0]+".jar")
        var jarFile:ZipOutputStream? = null
        if (!apkFile.apkConfig.useClassFiles()){
            if (!classJar.exists()) classJar.createNewFile()
            jarFile = ZipOutputStream(FileOutputStream(classJar))
        }
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
                if(pathFilter(it.first)) return@also
                if (!apkFile.apkConfig.useClassFiles()){
                    jarFile?.putNextEntry(ZipEntry(it.first.replace('\\','/')+it.second+".class"))
                    jarFile?.write(data)
                    return@also
                }
                try {
                    val f = File(File(out,it.first).apply {if (!exists()) mkdirs()},it.second+".class").apply { writeBytes(data) }
                    if (apkFile.apkConfig.jarWithDebugInfo()){
                        AppdbgDecompiler(f).decompile()
                    }
                }catch (e:Exception){
                    e.printStackTrace();
                }
            }
        }
        if (!apkFile.apkConfig.useClassFiles()) {
            jarFile?.closeEntry()
            jarFile?.close()
        }
    }

    /**
     * @param dexFile 源dexFile
     * @param out 目的文件夹
     */
    fun releaseDexClassFile(dexFile: File,out: File,apkFile: ApkFile) =
        releaseByteArrDexClassFile(dexFile.readBytes(),dexFile.name,out,apkFile)

    fun generateDevelopJar(out:File,generateJar:Boolean){
        //make jar debug libs for develop mode
        if (generateJar){
            ZipUtility.zip(out.path,File(File("${CommonConf.workDir}${File.separator}libs${File.separator}${CommonConf.appJarDir}").apply {
                if (!exists()) mkdirs()
                else {
                    deleteRecursively()
                    mkdirs()
                }},Random.nextULong().toString()+".jar").canonicalPath ){
                val temp = Path(out.path).relativize(Path(it.path))
                temp.startsWith("android") || temp.startsWith("kotlin")

            }
        }
    }

}