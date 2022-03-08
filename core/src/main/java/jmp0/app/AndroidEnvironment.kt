package jmp0.app

import javassist.ClassPool
import jmp0.apk.ApkFile
import jmp0.app.interceptor.intf.INativeInterceptor
import jmp0.app.clazz.ClassLoadedCallbackBase
import jmp0.conf.CommonConf
import jmp0.util.DexUtils
import org.apache.log4j.Logger
import java.io.File
import java.util.*

class AndroidEnvironment(private val apkFile: ApkFile,
                         val nativeInterceptor: INativeInterceptor,
                         private val absAndroidRuntimeClass: ClassLoadedCallbackBase = object :
                             ClassLoadedCallbackBase(){}) {
    private val logger = Logger.getLogger(javaClass)
    private val loader = XAndroidDexClassLoader(this)
    val id = UUID.randomUUID().toString()
    var processName = id

    init {
        //create temp dir
        File(CommonConf.tempDirName).apply { if (!exists()) mkdir() }
        checkAndReleaseFramework()
        loadUserSystemClass()
        registerToContext()
    }

    /**
     * if you don't want to use any more,
     * please invoke this method
     */
    fun destroy(){
        DbgContext.unRegister(id)
    }

    fun setProcessName(name: String) = apply { processName = name }

    private fun registerToContext(){
        DbgContext.register(uuid = id,this)
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
        // TODO: 2022/3/8 模块化这个功能
        val path = "core/src/main/java/jmp0/app/clazz/system".replace("/",File.separator)
        val packageName = "jmp0.app.clazz.system"
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


    private fun loadClass(file: File): Class<*> {
        val data = absAndroidRuntimeClass.afterResolveClass(
            this,ClassPool.getDefault().makeClass(file.inputStream(),false)
        ).toBytecode()
        return loader.xDefineClass(null,data,0,data.size)
    }

    /**
     * @param className 形如 com.example.myapplication.TestJava
     * @return class类对象
     */
    fun loadClass(className: String): Class<*> {
       return absAndroidRuntimeClass.beforeResolveClass(this,className,loader)
       // TODO: 2022/3/8 找不到的时候throw出异常
           ?:loadClass(androidFindClass(className)?:throw Exception("$className not find from frame work")).apply { logger.debug("$this loaded!") }
    }

    fun findClass(name: String) = Class.forName(name,false,loader)

    override fun toString(): String =
        "${javaClass.simpleName}[$processName ]"

}