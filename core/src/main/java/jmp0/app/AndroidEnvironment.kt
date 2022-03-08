package jmp0.app

import javassist.ClassPool
import jmp0.apk.ApkFile
import jmp0.app.interceptor.mtd.INativeInterceptor
import jmp0.app.interceptor.runtime.AndroidRuntimeClassInterceptorBase
import jmp0.conf.CommonConf
import jmp0.util.DexUtils
import org.apache.log4j.Logger
import java.io.File

class AndroidEnvironment(private val apkFile: ApkFile,
                         nativeInterceptor: INativeInterceptor,
                         private val absAndroidRuntimeClass: AndroidRuntimeClassInterceptorBase = object :
                             AndroidRuntimeClassInterceptorBase(){}) {
    private val logger = Logger.getLogger(javaClass)
    private val loader = XAndroidDexClassLoader(this)
    companion object{
        //callback from native call of app
        // TODO: 2022/3/8 pass the instance to interceptor, support for program isolation.
        var gNativeInterceptor: INativeInterceptor? = null
    }

    init {
        //create temp dir
        File(CommonConf.tempDirName).apply { if (!exists()) mkdir() }
        checkAndReleaseFramework()
        gNativeInterceptor = nativeInterceptor
        loadUserSystemClass()
    }

    /**
     * must bypass jdk security check
     * must bypass jdk security check
     * must bypass jdk security check
     *  ${jdkPath}/Home/jre/lib/server/libjvm.dylib
     *  characteristic string => Prohibited package name:
     *  modify java/ to xxxxx before characteristic string
     */
    private fun loadUserSystemClass(){
        val path = "core/src/main/java/jmp0/app/runtime/system".replace("/",File.separator)
        val packageName = "jmp0.app.runtime.system"
        File(path).listFiles()!!.forEach {
            if (it.isFile){
                val fullClassName = packageName +'.'+ it.name.split('.')[0]
                val systemName = Class.forName(fullClassName).getDeclaredField("xxClassName").get(null) as String
                absAndroidRuntimeClass.loadToClass(systemName,fullClassName,loader)
            }
        }

    }

    private fun checkAndReleaseFramework(){
        val frameworkDir = File("${CommonConf.tempDirName}${File.separator}${CommonConf.frameworkDirName}")
        if(!frameworkDir.exists()){
            frameworkDir.mkdir()
            DexUtils.releaseApkClassFile(File(CommonConf.frameworkFileName),frameworkDir)
        }
    }

    private data class DefineStatus(val clazz: Class<*>?,val className:String)

    private fun privateDefineClass(name: String?,data: ByteArray,off:Int,size:Int): DefineStatus {
        return try {
            DefineStatus(loader.xDefineClass(name,data,off,data.size),"")
        }catch (e:ClassNotFoundException){
            DefineStatus(null,e.message!!)
        }catch (e:NoClassDefFoundError){
            DefineStatus(null,e.message!!)
        }
    }

    private fun androidFindClass(className: String):File?=
        findFromApkFile(className)?:findFromAndroidFramework(className)


    private fun findFromDir(dir:File,className:String):File?{
        val fileName = className.replace('.','_').replace('/','_')+".class"
        dir.listFiles()!!.forEach {
            if(it.name == fileName)
                return it
        }
        return null
    }

    private fun findFromApkFile(className: String):File? =
        findFromDir(apkFile.classesDir,className)

    private fun findFromAndroidFramework(className: String): File? =
        findFromDir(File(CommonConf.tempDirName+File.separator+CommonConf.frameworkDirName),className)


    private fun loadClass(file: File): Class<*>? {
        val data = absAndroidRuntimeClass.afterFindClassFile(
            ClassPool.getDefault().makeClass(file.inputStream())
        ).toBytecode()
        val res = privateDefineClass(null,data,0,data.size)
        if(res.clazz != null) return res.clazz
        logger.error("${res.className} load error")
        return null
    }

    /**
     * @param className 形如 com.example.myapplication.TestJava
     * @return class类对象
     */
    fun loadClass(className: String): Class<*>? {
       return absAndroidRuntimeClass.beforeResolveClass(className,loader)
           ?:loadClass(androidFindClass(className)!!).apply { logger.debug("$this loaded!") }
    }

}