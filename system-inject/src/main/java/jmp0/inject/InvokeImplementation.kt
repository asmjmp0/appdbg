package jmp0.inject

import com.sun.nio.zipfs.ZipFileSystem
import jmp0.inject.intf.IInject
import java.io.*
import java.net.URI
import java.nio.file.FileSystem
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.spi.FileSystemProvider
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/7
 */
class InvokeImplementation(private val implPath:String,private val packageName:String) {

    private val classInfoList = mutableListOf<IInject.ClassInfo>()

    private fun fileClassInfo(name:String): IInject.ClassInfo {
        val fullClassName = packageName+'.'+name.split('.')[0]
        val clazz = Class.forName(fullClassName)
        val generateMethod = clazz.getDeclaredMethod("generateClass")
        val ins = clazz.getDeclaredConstructor().newInstance()
        return generateMethod.invoke(ins) as IInject.ClassInfo
    }

    fun invoke(){
        //get each class
        File(implPath).listFiles()!!.forEach {
            classInfoList.add(fileClassInfo(it.name))
        }


        val zipInputStream = ZipOutputStream(BufferedOutputStream(FileOutputStream(File("libs/dbg_inject.jar"))))
        classInfoList.forEach {
            println("generate ${it.name} class")
            zipInputStream.putNextEntry(ZipEntry(it.name.replace('.','/')+".class"))
            zipInputStream.write(it.data)
        }
        println("complete!")
        zipInputStream.close()
    }
}