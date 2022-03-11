package jmp0.app

import com.googlecode.d2j.util.zip.ZipFile
import javassist.ClassPool
import javassist.CtClass
import jmp0.apk.ApkFile
import jmp0.app.interceptor.intf.IInterceptor
import jmp0.app.clazz.ClassLoadedCallbackBase
import jmp0.conf.CommonConf
import jmp0.util.UnzipUtility
import org.apache.log4j.Logger
import java.io.File
import java.net.URLClassLoader
import java.util.*
import java.util.jar.JarFile
import java.util.zip.ZipEntry

// TODO: 2022/3/9 模拟初始化Android activity，并载入自定义类加载器
class AndroidEnvironment(private val apkFile: ApkFile,
                         val methodInterceptor: IInterceptor,
                         private val absAndroidRuntimeClass: ClassLoadedCallbackBase = object :
                             ClassLoadedCallbackBase(){
                             override fun afterResolveClassImpl(
                                 androidEnvironment: AndroidEnvironment,
                                 ctClass: CtClass
                             ): CtClass {
                                 return ctClass
                             }

                             override fun beforeResolveClassImpl(
                                 androidEnvironment: AndroidEnvironment,
                                 className: String,
                                 classLoader: XAndroidClassLoader
                             ): Class<*>? {
                                 return null
                             }
                         }) {
    private val logger = Logger.getLogger(javaClass)
    private val androidLoader = XAndroidClassLoader(this)
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

    private fun loadAndroidClass(jarFile: JarFile,ba:ByteArray){
        while (true){
            try {
                androidLoader.xDefineClass(null,ba,0,ba.size)
                break
            }catch (e:FrameWorkClassNoFoundException){
                logger.debug("")

            }
        }
    }

    // TODO: 2022/3/11 load android jar
    private fun loadAndroidMockJar(){
        val path = CommonConf.mockAndroidJar
        val jarFile = JarFile(File(path))
        val entry = jarFile.entries()
        while(entry.hasMoreElements()){
            val e = entry.nextElement()
            val name = e.name.replace('/','.')
            if (!e.isDirectory and name.startsWith("jmp0.mock.") and name.endsWith(".class")){
                val originClassName = name.replace(".class","")
                val fullClassName = name.replace("jmp0.mock.","").replace(".class","")
                val stream = jarFile.getInputStream(e)
                val ctClass = ClassPool.getDefault().makeClass(stream)
                ctClass.replaceClassName(originClassName,fullClassName)
                val byteCode = ctClass.toBytecode()
                loadAndroidClass(jarFile,byteCode)
            }
        }
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
        val packageName = CommonConf.userSystemClassPackageName
        val path = CommonConf.mockSystemClass
        File(path).listFiles()!!.forEach {
            if (it.isFile){
                val fullClassName = packageName +'.'+ it.name.split('.')[0]
                val systemName = Class.forName(fullClassName).getDeclaredField("xxClassName").get(null) as String
                absAndroidRuntimeClass.loadToClass(systemName,fullClassName,androidLoader)
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
        return androidLoader.xDefineClass(null,data,0,data.size)
    }

    /**
     * look for it from apk path
     * @param className 形如 com.example.myapplication.TestJava
     * @return class类对象
     */
    fun loadClass(className: String): Class<*> {
        val mClassName = className.replace("[]","")
        return absAndroidRuntimeClass.beforeResolveClass(this,mClassName,androidLoader)
           ?:loadClass(androidFindClass(mClassName)?:throw FrameWorkClassNoFoundException("$mClassName not find from frame work")).apply { logger.trace("$this loaded!") }
    }

    /**
     * look for it from this project
     * this will not pass the resolve-callback
     * @param className 形如 com.example.myapplication.TestJava
     * @return class类对象
     */
    fun loadClassProject(className: String): Class<*>{
        val ctClass = ClassPool.getDefault().getCtClass(className)
        return ctClass.toBytecode().run {
            ctClass.defrost()
            androidLoader.xDefineClass(null,this,0,size)
        }
    }

    fun findClass(name: String)
        = Class.forName(name,false,androidLoader)

    /**
     * @param signature which looks like xxxxx
     * @param implemented if it is ture the method while be replace by your method
     */
    fun registerMethodHook(signature:String,replace:Boolean)
        = DbgContext.registerMethodHook(id,signature,replace)

    override fun toString(): String =
        "${javaClass.simpleName}[$processName ]"

}