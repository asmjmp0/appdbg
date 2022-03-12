package jmp0.app.mock.ntv

import jmp0.app.DbgContext
import jmp0.app.mock.MockedBy
import org.apache.log4j.Logger

object BinderInternal {
    private val logger = Logger.getLogger(BinderInternal::class.java)

    @JvmStatic
    @MockedBy("asmjmp0")
    fun getContextObject(uuid:String):Any{
        val env = DbgContext.getAndroidEnvironment(uuid)
        return env!!.findClass("jmp0.app.mock.system.service.ServiceManager").getDeclaredConstructor().newInstance()
    }
}