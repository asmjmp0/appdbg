package jmp0.appdbg.external.impl

import jmp0.appdbg.external.Common
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

abstract class GeneratorBase {
    protected val dir = "temp${File.separator}JDK${File.separator}${Common.systemNowPackage}"

    fun writeToRTJAR(){
        val writeList = java.util.ArrayList<String>().apply {
            val pp: Path = Paths.get(dir)
            Files.walk(pp)
                .filter { path -> !Files.isDirectory(path) }
                .forEach { path ->
                    add(pp.relativize(path).toString())
                }
        }

        val path = System.getProperty("java.home")
        val rtFile = File(path,"lib${File.separator}rt.jar")
        val rtZipFile = ZipFile(rtFile)
        println("source rt.jar path is ${rtFile.canonicalPath}")
        val rtCopyFile = File("temp${File.separator}JDK","rt.jar").apply { if (!exists()) createNewFile()}
        val zs = ZipOutputStream(rtCopyFile.outputStream())
        rtZipFile.entries().iterator().forEach {
            if (it.name in writeList){
                zs.putNextEntry(ZipEntry(it.name))
                zs.write(File(dir,it.name).readBytes())

                writeList.remove(it.name)
            } else{
                zs.putNextEntry(it)
                zs.write(rtZipFile.getInputStream(it).readBytes())
            }
            zs.closeEntry()
        }

        writeList.forEach{
            zs.putNextEntry(ZipEntry(it))
            zs.write(File(dir,it).readBytes())
            zs.closeEntry()
        }
        println("dest rt.jar path is ${rtCopyFile.canonicalPath}")
        println("write completed!")
    }
}