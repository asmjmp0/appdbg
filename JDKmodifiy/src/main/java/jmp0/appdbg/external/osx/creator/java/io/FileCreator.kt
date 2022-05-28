package jmp0.appdbg.external.osx.creator.java.io

import javassist.CtClass
import javassist.CtField
import javassist.bytecode.AccessFlag
import jmp0.appdbg.external.ClassCreatorBase
import jmp0.appdbg.external.Common
import java.lang.Exception

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/5/17
 */
class FileCreator:ClassCreatorBase(getTargetClassName(FileCreator::class.java.`package`.name,"File"),CreatorType.RUNTIME) {
    override fun createImpl(ctClass: CtClass): CtClass {
        ctClass.declaredConstructors.forEach {
            if ((it.modifiers) and (AccessFlag.PUBLIC) == 1){
                when(it.signature){
                    "(Ljava/lang/String;)V"->{
                        it.insertBefore("""
                            try {
                                Class clazz = Class.forName("java.io.PathInterceptorManager");
                                java.lang.reflect.Method getInstanceMethod = clazz.getDeclaredMethod("getInstance",new Class[]{});
                                Object ins = getInstanceMethod.invoke(null,new Object[]{});
                                java.lang.reflect.Field nameInterceptorField = clazz.getField("nameInterceptor");
                                Object mNameInterceptor = nameInterceptorField.get(ins);
                                if (mNameInterceptor != null){
                                    java.lang.reflect.Method pathFilter = mNameInterceptor.getClass().getDeclaredMethod("pathFilter",new Class[]{Object.class});
                                    pathFilter.setAccessible(true);
                                    $1 = (String) pathFilter.invoke(mNameInterceptor,new Object[]{$1});
                                }
                            } catch (Exception ignore) {}
                    """.trimIndent())
                    }
                    "(Ljava/lang/String;Ljava/lang/String;)V"->{
                        it.insertBefore("""
                            try {
                                Class clazz = Class.forName("java.io.PathInterceptorManager");
                                java.lang.reflect.Method getInstanceMethod = clazz.getDeclaredMethod("getInstance",new Class[]{});
                                Object ins = getInstanceMethod.invoke(null,new Object[]{});
                                java.lang.reflect.Field nameInterceptorField = clazz.getField("nameInterceptor");
                                Object mNameInterceptor = nameInterceptorField.get(ins);
                                if (mNameInterceptor != null){
                                    java.lang.reflect.Method pathFilter = mNameInterceptor.getClass().getDeclaredMethod("pathFilter",new Class[]{Object.class});
                                    pathFilter.setAccessible(true);
                                    $1 = (String) pathFilter.invoke(mNameInterceptor,new Object[]{$1});
                                    $2 = (String) pathFilter.invoke(mNameInterceptor,new Object[]{$2});
                                }
                            } catch (Exception ignore) {}
                        """.trimIndent())
                    }
                    "(Ljava/io/File;Ljava/lang/String;)V"->{
                        it.insertBefore("""
                             try {
                                 Class clazz = Class.forName("java.io.PathInterceptorManager");
                                 java.lang.reflect.Method getInstanceMethod = clazz.getDeclaredMethod("getInstance",new Class[]{});
                                 Object ins = getInstanceMethod.invoke(null,new Object[]{});
                                 java.lang.reflect.Field nameInterceptorField = clazz.getField("nameInterceptor");
                                 Object mNameInterceptor = nameInterceptorField.get(ins);
                                 if (mNameInterceptor != null){
                                     java.lang.reflect.Method pathFilter = mNameInterceptor.getClass().getDeclaredMethod("pathFilter",new Class[]{Object.class});
                                     pathFilter.setAccessible(true);
                                     //the first param is created by other constructor
                                     $2 = (String) pathFilter.invoke(mNameInterceptor,new Object[]{$2});
                                 }
                             } catch (Exception ignore) {}
                        """.trimIndent())
                    }
                    "(Ljava/net/URI;)V"->{
                        it.insertBefore("""
                             try {
                                 Class clazz = Class.forName("java.io.PathInterceptorManager");
                                 java.lang.reflect.Method getInstanceMethod = clazz.getDeclaredMethod("getInstance",new Class[]{});
                                 Object ins = getInstanceMethod.invoke(null,new Object[]{});
                                 java.lang.reflect.Field nameInterceptorField = clazz.getField("nameInterceptor");
                                 Object mNameInterceptor = nameInterceptorField.get(ins);
                                 if (mNameInterceptor != null){
                                     java.lang.reflect.Method pathFilter = mNameInterceptor.getClass().getDeclaredMethod("pathFilter",new Class[]{Object.class});
                                     pathFilter.setAccessible(true);
                                     $1 = new java.net.URI((String) pathFilter.invoke(mNameInterceptor,new Object[]{$1}));
                                 }
                             } catch (Exception ignore) {}
                        """.trimIndent())
                    }
                    else -> throw Exception("${it.signature} Constructor not defined")
                }
            }
        }
        return ctClass
    }
}