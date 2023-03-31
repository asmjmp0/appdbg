package suites

import TestBase
import jmp0.app.AndroidEnvironment
import jmp0.app.conversation.AppdbgConversationSchemaEnum
import jmp0.app.conversation.IAppdbgConversation
import jmp0.app.conversation.IAppdbgConversationHandler
import jmp0.app.conversation.impl.sp.SharedPreferencesConversation
import jmp0.app.interceptor.intf.IInterceptor
import org.junit.jupiter.api.Test

class SharedPreferencesTest:TestBase() {

    @Test override fun test() {
        val ae = AndroidEnvironment(TestUtil.testApkFile, object : IInterceptor {
            override fun nativeCalled(
                uuid: String,
                className: String,
                funcName: String,
                signature: String,
                param: Array<out Any?>
            ): IInterceptor.ImplStatus {
                return IInterceptor.ImplStatus(false,null)
            }

            override fun methodCalled(
                uuid: String,
                className: String,
                funcName: String,
                signature: String,
                param: Array<out Any?>
            ): Any? {
                TODO("Not yet implemented")
            }

            override fun ioResolver(path: String): IInterceptor.ImplStatus {
                return IInterceptor.ImplStatus(false,"");
            }

        })

        ae.setConversationHandler(AppdbgConversationSchemaEnum.SHARED_PREFERENCES,object : IAppdbgConversationHandler {
            override fun appdbgConversationHandle(
                androidEnvironment: AndroidEnvironment,
                conversation: IAppdbgConversation<*>
            ): IInterceptor.ImplStatus {
                with(conversation as SharedPreferencesConversation){
                    val keyName = conversation.data.params[0] as String
                    when(conversation.data.methodName){
                        "getString"->{
                            if (keyName == "test1"){
                                return IInterceptor.ImplStatus(true,"SharedPreferencesConversation Test")
                            }
                        }
                        "getInt"->{
                            if (keyName == "test2"){
                                return IInterceptor.ImplStatus(true,666)
                            }
                        }

                    }
                }
                return IInterceptor.ImplStatus(false,null)
            }
        })
        val contextClazz = ae.findClass("android.content.Context")
        val clazz = ae.loadClass("jmp0.test.testapp.SharedPreferencesTest")
        val ins = clazz.getDeclaredConstructor(contextClazz).newInstance(ae.context)
        clazz.getDeclaredMethod("testAll").invoke(ins)
    }
}