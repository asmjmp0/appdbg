package jmp0.app.mock.ntv

import jmp0.app.DbgContext
import jmp0.app.mock.NativeHookClass
import jmp0.app.mock.NativeHookReturnType
import org.apache.log4j.Logger
@NativeHookClass("com.android.internal.os.BinderInternal")
object BinderInternal {
    private val logger = Logger.getLogger(BinderInternal::class.java)

    @JvmStatic
    @NativeHookReturnType("Landroid/os/IBinder;")
    fun getContextObject(uuid:String):Any{
        val env = DbgContext.getAndroidEnvironment(uuid)
        return env!!.findClass("jmp0.app.mock.system.service.ServiceManager").getDeclaredConstructor().newInstance()
    }
}