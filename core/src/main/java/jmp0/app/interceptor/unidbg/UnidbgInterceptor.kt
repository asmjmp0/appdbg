package jmp0.app.interceptor.unidbg

import com.github.unidbg.arm.backend.BackendFactory
import com.github.unidbg.arm.backend.DynarmicFactory
import com.github.unidbg.file.linux.AndroidFileIO
import com.github.unidbg.linux.android.AndroidARMEmulator
import com.github.unidbg.linux.android.AndroidResolver
import com.github.unidbg.linux.android.dvm.*
import com.github.unidbg.linux.android.dvm.wrapper.DvmInteger
import com.github.unidbg.memory.SvcMemory
import com.github.unidbg.unix.UnixSyscallHandler
import jmp0.app.AndroidEnvironment
import jmp0.app.DbgContext
import jmp0.app.interceptor.intf.IInterceptor
import jmp0.util.ReflectUtilsBase
import jmp0.util.SystemReflectUtils
import org.apache.log4j.Logger
import java.io.File
import java.lang.IllegalArgumentException
import java.util.LinkedList

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/10
 */
abstract class UnidbgInterceptor(private val soName:String): IInterceptor {
    private val logger = Logger.getLogger(UnidbgInterceptor::class.java)

    private var androidEnvironment:AndroidEnvironment? = null
    private var emulator:AndroidARMEmulator? = null
    private lateinit var vm: VM
    private lateinit var md:DalvikModule
    private val objList:ArrayList<DvmObjectWrapper> = ArrayList()



    private fun checkUnidbgEnv(uuid: String){
        if (androidEnvironment == null)
            androidEnvironment = DbgContext.getAndroidEnvironment(uuid)
        if (emulator == null){
            emulator = object :AndroidARMEmulator(androidEnvironment!!.apkFile.packageName,null,
                setOf<BackendFactory>(DynarmicFactory(true))){
                override fun createSyscallHandler(svcMemory: SvcMemory?): UnixSyscallHandler<AndroidFileIO> {
                    return super.createSyscallHandler(svcMemory)
                }
            }
            emulator!!.memory.setLibraryResolver(AndroidResolver(23))
            vm = emulator!!.createDalvikVM(androidEnvironment!!.apkFile.copyApkFile)

            vm.setJni(object :AbstractJni(){
                override fun callStaticObjectMethodV(
                    vm: BaseVM,
                    dvmClass: DvmClass,
                    dvmMethod: DvmMethod,
                    vaList: VaList
                ): DvmObject<*> {
                    logger.info("from jni call ${dvmClass.className}.${dvmMethod.methodName}${dvmMethod.args} pass to appdbg!!")
                    val clazzName = dvmClass.className.replace('/','.')
                    val methodName = dvmMethod.methodName
                    val signatureInfo = SystemReflectUtils.getSignatureInfo(clazzName+'.'+methodName+dvmMethod.args,androidEnvironment!!.id)
                    val clazz = androidEnvironment!!.findClass(clazzName)
                    val method = clazz.getDeclaredMethod(methodName,*signatureInfo.paramTypes)
                    val params = toOriginalObject(vaList,signatureInfo)
                    val res = method.invoke(null,*params)
                    return toUnidbgObject(res)!!
                }

                override fun newObjectV(
                    vm: BaseVM?,
                    dvmClass: DvmClass,
                    dvmMethod: DvmMethod,
                    vaList: VaList
                ): DvmObject<*> {
                    logger.info("new object ${dvmClass.className}.${dvmMethod.methodName}${dvmMethod.args} pass to appdbg!!")
                    val clazzName = dvmClass.className.replace('/', '.')
                    val methodName = dvmMethod.methodName
                    val signatureInfo = SystemReflectUtils.getSignatureInfo(
                        clazzName + '.' + methodName + dvmMethod.args,
                        androidEnvironment!!.id
                    )
                    val clazz = androidEnvironment!!.findClass(clazzName)
                    val method = clazz.getDeclaredConstructor(*signatureInfo.paramTypes)
                    val params = toOriginalObject(vaList, signatureInfo)
                    val obj = toUnidbgObject(method.newInstance(*params))!!
                    objList.add(obj)
                    return obj
                }

                override fun callVoidMethodV(
                    vm: BaseVM,
                    dvmObject: DvmObject<*>,
                    dvmMethod: DvmMethod,
                    vaList: VaList
                ) {
                    logger.info("call method ${dvmObject.objectType.className}.${dvmMethod.methodName}${dvmMethod.args} pass to appdbg!!")
                    val s = objList.find { it == dvmObject }?:throw Exception("$dvmObject no found")
                    val clazzName = dvmObject.objectType.className.replace('/','.')
                    val methodName = dvmMethod.methodName
                    val signatureInfo = SystemReflectUtils.getSignatureInfo(
                        clazzName + '.' + methodName + dvmMethod.args,
                        androidEnvironment!!.id
                    )
                    val clazz = androidEnvironment!!.findClass(clazzName)
                    val method = clazz.getDeclaredMethod(methodName,*signatureInfo.paramTypes)
                    val params = toOriginalObject(vaList,signatureInfo)
                    method.invoke(s.obj,*params)
                }

            })
            md = vm.loadLibrary((soName.split('.')[0]).substring(3),
                File(androidEnvironment!!.apkFile.nativeLibraryDir,soName).readBytes(),true)
            md.callJNI_OnLoad(emulator)
        }
    }

    private fun toOriginalObject(vaList: VaList,signatureInfo: ReflectUtilsBase.SignatureInfo):Array<Any?>{
        if (signatureInfo.paramTypes.isEmpty()) return emptyArray()
        val retArr = ArrayList<Any?>()
        val size = signatureInfo.paramTypes.size
        for (i in 0 until size){
            when(signatureInfo.paramTypes[i].name){
                "int"-> {
                    val intObj = vaList.getIntArg(i)
                    retArr.add(intObj)
                }
                "double"-> {
                    val doubleObj = vaList.getDoubleArg(i)
                    retArr.add(doubleObj)
                }
                "float"-> {
                    val floatObj = vaList.getFloatArg(i)
                    retArr.add(floatObj)
                }
                "long"-> {
                    val longObj = vaList.getLongArg(i)
                    retArr.add(longObj)
                }
                else -> {
                    val dvmObj = vaList.getObjectArg<DvmObject<*>>(i)
                    retArr.add(dvmObj.value)
                }
            }

        }
        return retArr.toArray()
    }

    private fun toUnidbgObject(obj:Any?):DvmObjectWrapper?{
        if (obj == null) return null
        val clazzName = obj.javaClass.name.replace(".","/")
        return DvmObjectWrapper(vm.resolveClass(clazzName),obj)
    }

    private fun wrapperToUnidbgParams(param: Array<out Any?>): Array<out Any> {
        val retArr = ArrayList<DvmObject<*>?>()
        if (param.isEmpty()) return retArr.toArray()
        param.forEach {
            retArr.add(toUnidbgObject(it))
        }
        return retArr.toArray()
    }

    private fun callUnidbgJniMethod(clazz: DvmClass,methodName:String,signature:String,signatureInfo: ReflectUtilsBase.SignatureInfo,param: Array<out Any?>): IInterceptor.ImplStatus {
        val params = wrapperToUnidbgParams(param)
        val res:Any? = when(signatureInfo.returnType!!){
            Int::class.java->{
                clazz.callStaticJniMethodInt(emulator,methodName+signature,*params)
            }
            // FIXME: 2022/3/30 other type need to implement
            else->{
                clazz.callStaticJniMethodObject<DvmObject<Any>>(emulator,methodName+signature,*params)
            }
        }
        return when(res){
            // FIXME: 2022/3/30 other type need to implement
            is Int->{
                IInterceptor.ImplStatus(implemented = true, result = res)
            }
            else ->{
                IInterceptor.ImplStatus(implemented = true, result = (res as DvmObject<*>).value)
            }
        }

    }

    abstract fun otherNativeCalled(uuid: String, className: String, funcName: String, signature: String, param: Array<out Any?>): IInterceptor.ImplStatus

    override fun nativeCalled(uuid: String, className: String, funcName: String, signature: String, param: Array<out Any?>): IInterceptor.ImplStatus {
        logger.info("call native method $className.$funcName$signature")
        checkUnidbgEnv(uuid)
        val clazz = vm.resolveClass(className.replace('.','/'))
        val signatureInfo = SystemReflectUtils.getSignatureInfo("$className.$funcName$signature",uuid)
        return try {
            callUnidbgJniMethod(clazz,funcName,signature,signatureInfo,param)
        }catch (e:IllegalArgumentException){
            //the native method not found in this module
            otherNativeCalled(uuid, className, funcName, signature, param)
        }
    }
}