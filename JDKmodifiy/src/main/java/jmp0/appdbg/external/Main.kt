package jmp0.appdbg.external

import jmp0.appdbg.external.creator.java.io.FileCreator
import jmp0.appdbg.external.creator.java.io.IPathInterceptorCreator
import jmp0.appdbg.external.creator.java.io.PathInterceptorManagerCreator
import java.io.File
import java.lang.reflect.Field
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.net.URI
import java.net.URL
import javax.print.attribute.standard.NumberUp

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/5/17
 */
class Main {
    companion object{
        val dir = "temp${File.separator}JDK"

        @JvmStatic
        fun main(args:Array<String>){
            try {
                val clazz = Class.forName("java.io.PathInterceptorManager")
                val iPathInterceptorClazz = Class.forName("java.io.IPathInterceptor")
                val method = clazz.getDeclaredMethod("getInstance")
                val ins = method.invoke(null);
                val field = clazz.getDeclaredField("nameInterceptor")
                field.set(ins,Proxy.newProxyInstance(null, arrayOf(iPathInterceptorClazz),object:InvocationHandler{
                    override fun invoke(proxy: Any?, method: Method, args: Array<out Any>): Any {
                        if(method.name == "pathFilter"){
                            when(args[0]){
                                is String->{
                                    val str = args[0]
                                    return str
                                }
                                is URI->{
                                    val uri = args[0] as URI
                                    return uri
                                }
                            }
                        }
                        return Any()
                    }

                }))
            }catch (e:Exception){}
            val generateList = listOf(IPathInterceptorCreator(), PathInterceptorManagerCreator() ,FileCreator())
            generateList.forEach {
                it.create(dir)
            }
        }
    }
}