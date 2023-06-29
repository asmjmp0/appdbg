package jmp0.app.classloader

import javassist.ClassPool
import jmp0.app.AndroidEnvironment
import jmp0.conf.CommonConf
import org.apache.log4j.Logger
import java.io.File
import java.io.InputStream

class ClassFinder(private val androidEnvironment: AndroidEnvironment,private val androidRuntimeClass: ClassLoadedCallbackBase) {
    private val logger = Logger.getLogger(javaClass)
    private val androidLoader:XAndroidClassLoader = androidEnvironment.getClassLoader()
    private val findPath = androidEnvironment.apkFile.classesDir
    private val findJar = androidEnvironment.apkFile.classesJar

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
            ?:loadClass(androidFindClass(className)?.inputStream()?:throw FrameWorkClassNoFoundException("$className not find from frame work")).apply { logger.trace("$this loaded!") }
    }

    private fun loadClassFromJar(className: String):Class<*>{
        TODO("not implemented yet!")
    }


    fun loadClass(className: String):Class<*>{
        val mClassName = className.replace("[]","")
        return if (!androidEnvironment.apkFile.apkConfig.notUseClassFiles()){
            loadClassFromPath(mClassName)
        }else loadClassFromJar(className)
    }

}