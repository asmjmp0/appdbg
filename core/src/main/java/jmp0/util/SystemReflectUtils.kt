package jmp0.util

import io.github.classgraph.ClassGraph
import jmp0.app.DbgContext

object SystemReflectUtils : ReflectUtilsBase() {
    private val classLoader = ClassLoader.getSystemClassLoader()
    fun String.findSystemClass() =
        Class.forName(this, false, DbgContext.appClassLoader)

    fun getAllClassWithAnnotation(
        packageName: String,
        annotation: Class<out Annotation>,
        callback: (fullClassName: String, isInner: Boolean) -> Unit
    ) {
        ClassGraph()
            .enableAllInfo()
            .acceptPackages(packageName)
            .scan(4).use { scanResult ->
                scanResult.allClasses.forEach { classInfo ->
                    //bugfix inner anonymous class load
                    if (!classInfo.isInnerClass) {
                        callback(classInfo.name, false)
                    } else {
                        callback(classInfo.name, true)
                    }
                }
            }
    }
}