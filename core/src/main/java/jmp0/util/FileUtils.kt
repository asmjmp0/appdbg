package jmp0.util

import java.io.File

object FileUtils {
    fun listFileRecursive(file: File, packageName:String = "", callback:(packageName:String, fileName:String)->Unit){
        file.listFiles()!!.forEach {
            if (it.isFile){
                callback(packageName,it.name)
            }
            if (it.isDirectory){
                listFileRecursive(it,packageName+"."+it.name,callback)
            }
        }
    }
}