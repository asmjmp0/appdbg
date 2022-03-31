package jmp0.app

import javassist.ClassPool
import javassist.CtClass
import javassist.CtField
import javassist.NotFoundException
import jmp0.apk.ApkFile
import jmp0.app.classloader.ClassLoadedCallbackBase
import jmp0.app.classloader.FrameWorkClassNoFoundException
import jmp0.app.classloader.XAndroidClassLoader
import jmp0.app.interceptor.intf.IInterceptor
import jmp0.app.mock.annotations.ClassReplaceTo
import jmp0.app.mock.MethodManager
import jmp0.conf.CommonConf
import jmp0.util.FileUtils
import jmp0.util.ZipUtility
import org.apache.log4j.Logger
import java.io.File
import java.util.*

// TODO: 2022/3/9 模拟初始化Android activity，并载入自定义类加载器
class AndroidEnvironment(val apkFile: ApkFile,
                         private val methodInterceptor: IInterceptor,
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
    var context:Any

    init {
        //create temp dir
        File(CommonConf.tempDirName).apply { if (!exists()) mkdir() }
        registerToContext()
        checkAndReleaseFramework()
        loadUserSystemClass()
        MethodManager(id).getMethodMap().forEach{
            registerMethodHook(it.key,true)
        }
        context = findClass("jmp0.app.mock.system.user.UserContext").getDeclaredConstructor().newInstance()
    }

    /**
     * if you don't want to use any more,
     * please invoke this method
     */
    fun destroy(){
        DbgContext.unRegister(id)
    }

    fun getMethodInterceptor() = methodInterceptor

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
        FileUtils.listFileRecursive(File(CommonConf.Mock.mockSystemClass),CommonConf.Mock.userSystemClassPackageName){ pName,fileName->
            val fullClassName = pName +'.'+ fileName.split('.')[0]
//                    val systemName = SystemClassManger.get(fullClassName)?:throw java.lang.Exception("$fullClassName not define in ${SystemClassManger.javaClass.name}")
            val ctClass = ClassPool.getDefault().getCtClass(fullClassName)
            val targetClassName = ctClass.annotations.find { annotation-> annotation is ClassReplaceTo }.run {
                if (this != null) (this as ClassReplaceTo).to
                else throw java.lang.Exception("${ClassReplaceTo::class.java} annotation is not added to $fullClassName")
            }

            if (targetClassName != "") ctClass.replaceClassName(fullClassName,targetClassName)
            //set uuid as xxUuid
            try {
                val field = ctClass.getDeclaredField("xxUuid")
                ctClass.removeField(field)
                ctClass.addField(CtField.make("public static String xxUuid = \"$id\";",ctClass))
            }catch (e: NotFoundException){
                ctClass.addField(CtField.make("public static String xxUuid = \"$id\";",ctClass))
            }
            val ba = ctClass.toBytecode()
            androidLoader.xDefineClass(null,ba,0,ba.size)
            ctClass.defrost()
        }

    }

    fun getClassLoader() = androidLoader

    private fun checkAndReleaseFramework(){
        val frameworkDir = File("${CommonConf.tempDirName}${File.separator}${CommonConf.frameworkDirName}")
        if(!frameworkDir.exists()){
            frameworkDir.mkdir()
            ZipUtility.unzip(CommonConf.frameworkFileName,frameworkDir.canonicalPath)
        }
    }

    private fun androidFindClass(className: String):File?=
        findFromApkFile(className)?:findFromAndroidFramework(className)


    private fun findFromDir(dir:File,className:String):File?{
        val filePath = className.replace('.','/')+".class"
        val f = File(dir,filePath)
        if (f.exists()) return f
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