package jmp0.util

import io.github.classgraph.ClassGraph
import io.github.classgraph.ClassInfo
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import kotlin.io.path.isDirectory
import kotlin.io.path.toPath


object SystemReflectUtils:ReflectUtilsBase() {
    private val classLoader = ClassLoader.getSystemClassLoader()
    fun String.findSystemClass() =
        Class.forName(this,false, classLoader)

    fun getAllClassWithAnnotation(packageName:String,annotation: Class<out Annotation>,callback:(fullClassName:String)->Unit){
        ClassGraph()
            .enableAnnotationInfo()
            .acceptPackages(packageName)
            .scan().use { scanResult ->
                scanResult.allClasses.forEach { classInfo->
                    if (!classInfo.name.contains('$')){
                        classInfo.annotations.forEach { annotationInfo->
                            if (annotationInfo.name == annotation.name){
                                callback(classInfo.name)
                            }
                        }
                    }
                }
            }
    }
}