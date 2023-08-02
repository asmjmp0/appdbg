package jmp0.app.classloader

import javassist.ClassPool
import jmp0.app.AndroidEnvironment
import jmp0.conf.CommonConf
import org.apache.log4j.Logger
import java.io.File
import java.io.InputStream
import java.util.jar.JarFile

class ClassFinder(private val androidEnvironment: AndroidEnvironment,private val androidRuntimeClass: ClassLoadedCallbackBase) {
    private val logger = Logger.getLogger(javaClass)
    private val androidLoader:XAndroidClassLoader = androidEnvironment.getClassLoader()
    private val findPath = androidEnvironment.apkFile.classesDir
    private val frameworkDir = File("${CommonConf.workDir}${File.separator}${CommonConf.tempDirName}${File.separator}${CommonConf.frameworkDirName}")
    private val frameworkJar = JarFile(File(frameworkDir,CommonConf.frameworkFileName))
    private val findJars = androidEnvironment.apkFile.classesDir.listFiles().let {
        HashSet<JarFile>().apply {
            it.forEach { file->
                if (file.name.endsWith(".jar")){
                    add(JarFile(file))
                }
            }
        }

    }

    private fun loadClass(inputStram: InputStream): Class<*> {
        val data = androidRuntimeClass.afterResolveClass(
            androidEnvironment, ClassPool.getDefault().makeClass(inputStram,false)
        ).toBytecode()
        return androidLoader.xDefineClass(null,data,0,data.size)
    }

    private fun androidFindClass(className: String):File?=
        findFromAndroidFramework(className)?:findFromApkFile(className)


    private fun findFromDir(dir:File,className:String):File?{
        val filePath = className.replace('.','/')+".class"
        val f = File(dir,filePath)
        if (f.exists()) return f
        return null
    }

    private fun findFromApkFile(className: String):File? =
        findFromDir(findPath,className)

    private fun findFromAndroidFramework(className: String): File? =
        findFromDir(File(CommonConf.workDir+File.separator+ CommonConf.tempDirName+File.separator+ CommonConf.frameworkDirName),className)

    private fun loadClassFromPath(className: String):Class<*>{
        return androidRuntimeClass.beforeResolveClass(this.androidEnvironment,className,androidLoader)
            ?:loadClass(androidFindClass(className)?.inputStream()?:throw FrameWorkClassNoFoundException("$className not find from unpacked class or framework")).apply { logger.trace("$this loaded!") }
    }


    private fun findInJar(jarFile: JarFile,className: String):Class<*>?{
        val name = className.replace('.','/')+".class"
        val res = jarFile.getJarEntry(name)
        return jarFile.getJarEntry(className.replace('.','/')+".class")?.let {
            loadClass(jarFile.getInputStream(it))
        }
    }
    private fun loadClassFromJarFrameWork(className: String):Class<*>? =
        findInJar(frameworkJar,className)

    private fun loadClassFromClassJar(className: String):Class<*>?{
        for (jar:JarFile in findJars){
            val res = findInJar(jar,className)
            if (res!=null) return res
        }
        return null
    }

    private fun loadClassFromJar(className: String):Class<*>{
        return androidRuntimeClass.beforeResolveClass(this.androidEnvironment,className,androidLoader)
            ?:(loadClassFromJarFrameWork(className)
            ?:loadClassFromClassJar(className)
            ?:throw FrameWorkClassNoFoundException("$className not find from unpacked class or framework")).apply { logger.trace("$this loaded!") }
    }


    fun loadClass(className: String):Class<*>{

        val mClassName = className.replace("[]","")
        return if (androidEnvironment.apkFile.apkConfig.useClassFiles()){
            loadClassFromPath(mClassName)
        }else loadClassFromJar(className)
    }

}