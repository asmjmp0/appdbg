package jmp0.appdbg.external.impl

import jmp0.appdbg.external.Common
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream

abstract class GeneratorBase {
    protected val dir = "temp${File.separator}JDK${File.separator}${Common.systemNowPackage}"

    fun writeToRTJAR(){
        val writeList = java.util.ArrayList<String>().apply l1@{
            if (!File(dir).exists()) return@l1
            val pp: Path = Paths.get(dir)
            Files.walk(pp)
                .filter { path -> !Files.isDirectory(path) }
                .forEach { path ->
                    add(pp.relativize(path).toString())
                }
        }

        val path = System.getProperty("java.home")
        val rtFile = File(path,"lib${File.separator}rt.jar")
        val rtJarFile = JarFile(rtFile)
        println("source rt.jar path is ${rtFile.canonicalPath}")
        val rtCopyFile = File("temp${File.separator}JDK","rt.jar").apply {
            if (!exists()){
                parentFile.mkdirs()
                createNewFile()
            }
        }
        val zs = JarOutputStream(rtCopyFile.outputStream())
        rtJarFile.entries().iterator().forEach {
            if (it.name in writeList){
                zs.putNextEntry(JarEntry(it.name))
                zs.write(File(dir,it.name).readBytes())
                writeList.remove(it.name)
            } else{
                zs.putNextEntry(JarEntry(it.name))
                zs.write(rtJarFile.getInputStream(it).readBytes())
            }
            zs.closeEntry()
        }

        writeList.forEach{
            zs.putNextEntry(JarEntry(it))
            zs.write(File(dir,it).readBytes())
            zs.closeEntry()
        }
        zs.close()
        println("dest rt.jar path is ${rtCopyFile.canonicalPath}")
        println("write completed!")
    }
}