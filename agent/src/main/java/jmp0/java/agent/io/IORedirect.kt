package jmp0.java.agent.io
/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/4/6
 */
import javassist.ClassPool
import jmp0.java.agent.IModifyRun
import java.io.File
import java.io.FileInputStream
import java.lang.instrument.ClassDefinition
import java.lang.instrument.Instrumentation
import java.util.jar.JarFile

class IORedirect: IModifyRun {
    private fun addFileInterceptor(instrumentation: Instrumentation){
        val workFile = File(System.getProperty("user.dir"))
        val bootstrapJar = File(workFile,"agent${File.separator}appdbg-bootstrap.jar")
        instrumentation.appendToBootstrapClassLoaderSearch(JarFile(bootstrapJar,false))
    }

    private fun modifyFileClassOpen(instrumentation: Instrumentation){
        val ctClass = ClassPool.getDefault().get("java.io.FileInputStream")
        val ctMethod = ctClass.getDeclaredMethod("open")
        ctMethod.insertBefore("""
                    Class clazz = Class.forName("jmp0.java.bootstrap.java.io.PathInterceptorManager");
                    java.lang.reflect.Method getInstanceMethod = clazz.getDeclaredMethod("getInstance",new Class[]{});
                    Object ins = getInstanceMethod.invoke(null,new Object[]{});
                    java.lang.reflect.Field nameInterceptorField = clazz.getField("nameInterceptor");
                    Object mNameInterceptor = nameInterceptorField.get(ins);
                    if (mNameInterceptor != null){
                        java.lang.reflect.Method pathFilter = mNameInterceptor.getClass().getDeclaredMethod("pathFilter",new Class[]{String.class});
                        $1 = (String) pathFilter.invoke(mNameInterceptor,new Object[]{$1});
                    }
        """.trimIndent())
        instrumentation.redefineClasses(ClassDefinition(FileInputStream::class.java,ctClass.toBytecode()))
        instrumentation.retransformClasses(FileInputStream::class.java)
    }

    override fun run(instrumentation: Instrumentation) {
        addFileInterceptor(instrumentation)
        modifyFileClassOpen(instrumentation)
    }
}