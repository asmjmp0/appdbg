package jmp0.app.mock.ntv

import jmp0.app.DbgContext
import jmp0.app.mock.MockedBy

object VMRuntime {

    @JvmStatic
    @MockedBy("asmjmp0")
    fun is64Bit(uuid:String) = true

    @JvmStatic
    @MockedBy("asmjmp0")
    fun newUnpaddedArray(uuid: String,clazz: Class<*>,len: Int):Any{
        val env = DbgContext.getAndroidEnvironment(uuid)
        val dClass = env!!.findClass(clazz.name)
        return java.lang.reflect.Array.newInstance(dClass,len)
    }

}