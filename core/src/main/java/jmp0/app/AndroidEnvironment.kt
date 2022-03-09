package jmp0.app

import javassist.ClassPool
import jmp0.apk.ApkFile
import jmp0.app.interceptor.intf.IInterceptor
import jmp0.app.clazz.ClassLoadedCallbackBase
import jmp0.conf.CommonConf
import jmp0.util.UnzipUtility
import org.apache.log4j.Logger
import java.io.File
import java.util.*

class AndroidEnvironment(private val apkFile: ApkFile,
                         val nativeInterceptor: IInterceptor,
                         private val absAndroidRuntimeClass: ClassLoadedCallbackBase = object :
                             ClassLoadedCallbackBase(){}) {
    private val logger = Logger.getLogger(javaClass)
    private val loader = XAndroidDexClassLoader(this,absAndroidRuntimeClass)
    val id = UUID.randomUUID().toString()
    var processName = id

    init {
        //create temp dir
        File(CommonConf.tempDirName).apply { if (!exists()) mkdir() }
        loadUserSystemClass()
        checkAndReleaseFramework()
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
        val path = CommonConf.userSystemClass
        val packageName = CommonConf.userSystemClassPackageName
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
            UnzipUtility.unzip(CommonConf.frameworkFileName,frameworkDir.canonicalPath)
        }
    }

    private fun androidFindClass(className: String):File?=
        findFromApkFile(className)?:findFromAndroidFramework(className)


    private fun findFromDir(dir:File,className:String,flag:Boolean=false):File?{
        if (flag){
            val filePath = className.replace('.','/')+".class"
            val f = File(dir,filePath)
            if (f.exists()) return f
            return null
        }else{
            val fileName = className.replace('.','/').replace('/','_')+".class"
            dir.listFiles()!!.forEach {
                if(it.name == fileName)
                    return it
            }
            return null
        }
    }

    private fun findFromApkFile(className: String):File? =
        findFromDir(apkFile.classesDir,className)

    private fun findFromAndroidFramework(className: String): File? =
        findFromDir(File(CommonConf.tempDirName+File.separator+CommonConf.frameworkDirName),className,true)


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
           ?:loadClass(androidFindClass(className)?:throw Exception("$className not find from frame work")).apply { logger.debug("$this loaded!") }
    }

    fun findClass(name: String)
        = Class.forName(name,false,loader)

    /**
     * @param signature which looks like xxxxx
     * @param implemented if it is ture the method while be replace by your method
     */
    fun registerMethodHook(signature:String,replace:Boolean)
        = DbgContext.registerMethodHook(id,signature,replace)

    override fun toString(): String =
        "${javaClass.simpleName}[$processName ]"

}