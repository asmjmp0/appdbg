package jmp0.app.interceptor.unidbg

import com.github.unidbg.AndroidEmulator
import com.github.unidbg.arm.backend.BackendFactory
import com.github.unidbg.file.linux.AndroidFileIO
import com.github.unidbg.linux.android.AndroidARMEmulator
import com.github.unidbg.linux.android.AndroidEmulatorBuilder
import com.github.unidbg.linux.android.AndroidResolver
import com.github.unidbg.linux.android.dvm.*
import com.github.unidbg.linux.android.dvm.wrapper.DvmInteger
import com.github.unidbg.memory.SvcMemory
import com.github.unidbg.unix.UnixSyscallHandler
import jmp0.app.AndroidEnvironment
import jmp0.app.DbgContext
import jmp0.app.interceptor.intf.IInterceptor
import jmp0.app.interceptor.unidbg.UnidbgWrapperUtils
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
    private var emulator:AndroidEmulator? = null
    private lateinit var vm: VM
    private lateinit var md:DalvikModule



    private fun checkUnidbgEnv(uuid: String){
        if (androidEnvironment == null)
            androidEnvironment = DbgContext.getAndroidEnvironment(uuid)
        if (emulator == null){
            emulator = AndroidEmulatorBuilder.for32Bit().build()
            emulator!!.memory.setLibraryResolver(AndroidResolver(23))
            vm = emulator!!.createDalvikVM(androidEnvironment!!.apkFile.copyApkFile)
            vm.setJni(AppdbgJni(vm,androidEnvironment!!))
            vm.setVerbose(true)
            md = vm.loadLibrary(File(androidEnvironment!!.apkFile.nativeLibraryDir,soName),true)
            md.callJNI_OnLoad(emulator)
        }
    }


    private fun callUnidbgJniMethod(clazz: DvmClass,methodName:String,signature:String,signatureInfo: ReflectUtilsBase.SignatureInfo,param: Array<out Any?>): IInterceptor.ImplStatus {
        val params = UnidbgWrapperUtils.wrapperToUnidbgParams(vm,param)
        val res:Any? = when(signatureInfo.returnType!!){
            Int::class.java-> clazz.callStaticJniMethodInt(emulator,methodName+signature,*params)
            Boolean::class.java-> clazz.callStaticJniMethodBoolean(emulator,methodName+signature,*params)
            Long::class.java -> clazz.callStaticJniMethodLong(emulator,methodName+signature,*params)
            // FIXME: 2022/3/30 other type need to implement
            else->{
                clazz.callStaticJniMethodObject<DvmObject<Any>>(emulator,methodName+signature,*params)
            }
        }
        return when(res){
            // FIXME: 2022/3/30 other type need to implement
            is Int,Boolean,Long-> IInterceptor.ImplStatus(implemented = true, result = res)
            else ->{
                IInterceptor.ImplStatus(implemented = true, result = if (res !=null) (res as DvmObject<*>).value else null)
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