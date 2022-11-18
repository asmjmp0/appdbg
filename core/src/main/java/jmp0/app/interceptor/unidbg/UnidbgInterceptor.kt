package jmp0.app.interceptor.unidbg

import com.github.unidbg.AndroidEmulator
import com.github.unidbg.linux.android.AndroidEmulatorBuilder
import com.github.unidbg.linux.android.AndroidResolver
import com.github.unidbg.linux.android.dvm.*
import jmp0.app.AndroidEnvironment
import jmp0.app.conversation.IAppdbgConversationData
import jmp0.app.conversation.impl.ntv.NativeData
import jmp0.app.interceptor.intf.IInterceptor
import jmp0.app.interceptor.intf.NativeImplementInterceptor
import jmp0.util.ReflectUtilsBase
import jmp0.util.SystemReflectUtils
import org.apache.log4j.Logger
import java.lang.IllegalArgumentException

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/10
 */
abstract class UnidbgInterceptor(private val autoLoad:Boolean): NativeImplementInterceptor {
    private val logger = Logger.getLogger(UnidbgInterceptor::class.java)
    private var emulator: AndroidEmulator = AndroidEmulatorBuilder.for32Bit().build().also {
        it.memory.setLibraryResolver(AndroidResolver(23))
    }
    private var vm: VM? = null

    override fun appdbgConversationHandle(
        androidEnvironment: AndroidEnvironment,
        data: IAppdbgConversationData
    ): IInterceptor.ImplStatus {
        if (!autoLoad) return IInterceptor.ImplStatus(false, null)
        with(data as NativeData) {
            logger.info("use auto load library soName-> $soName")
            loadLibrary(androidEnvironment, soName)
        }
        return IInterceptor.ImplStatus(false, null)
    }

    override fun loadLibrary(androidEnvironment: AndroidEnvironment, soName: String) {
        checkAndCreateVMAndJni(androidEnvironment)
        vm!!.loadLibrary(soName, true).callJNI_OnLoad(emulator)
    }

    private fun checkAndCreateVMAndJni(androidEnvironment: AndroidEnvironment) = synchronized(this::vm) {
        if (this.vm != null) return@synchronized
        this.vm = emulator.createDalvikVM(androidEnvironment.apkFile.copyApkFile)
        this.vm!!.setJni(AppdbgJni(this.vm!!, androidEnvironment))
        this.vm!!.setVerbose(true)
    }


    private fun callUnidbgJniMethod(
        clazz: DvmClass,
        methodName: String,
        signature: String,
        signatureInfo: ReflectUtilsBase.SignatureInfo,
        param: Array<out Any?>
    ): IInterceptor.ImplStatus {
        val params = UnidbgWrapperUtils.wrapperToUnidbgParams(vm!!, param, signatureInfo)
        val res: Any? = when (signatureInfo.returnType!!) {
            Int::class.java -> clazz.callStaticJniMethodInt(emulator, methodName + signature, *params)
            Boolean::class.java -> clazz.callStaticJniMethodBoolean(emulator, methodName + signature, *params)
            Long::class.java -> clazz.callStaticJniMethodLong(emulator, methodName + signature, *params)
            // FIXME: 2022/3/30 other type need to implement
            else -> {
                clazz.callStaticJniMethodObject<DvmObject<Any>>(emulator, methodName + signature, *params)
            }
        }
        return when (res) {
            // FIXME: 2022/3/30 other type need to implement
            is Int, Boolean, Long -> IInterceptor.ImplStatus(implemented = true, result = res)
            else -> {
                IInterceptor.ImplStatus(
                    implemented = true,
                    result = if (res != null) (res as DvmObject<*>).value else null
                )
            }
        }

    }

    abstract fun otherNativeCalled(
        uuid: String,
        className: String,
        funcName: String,
        signature: String,
        param: Array<out Any?>
    ): IInterceptor.ImplStatus

    override fun nativeCalled(
        uuid: String,
        className: String,
        funcName: String,
        signature: String,
        param: Array<out Any?>
    ): IInterceptor.ImplStatus {
        logger.info("call native method $className.$funcName$signature")
        val clazz = vm!!.resolveClass(className.replace('.', '/'))
        val signatureInfo = SystemReflectUtils.getSignatureInfo("$className.$funcName$signature", uuid)
        return try {
            callUnidbgJniMethod(clazz, funcName, signature, signatureInfo, param)
        } catch (e: IllegalArgumentException) {
            //the native method not found in this module
            otherNativeCalled(uuid, className, funcName, signature, param)
        }
    }
}