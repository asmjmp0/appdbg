package jmp0.appdbg.external.java.io;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/5/17
 */
public final class PathInterceptorManager {
    private PathInterceptorManager(){ }

    public Object nameInterceptor = null;

    public volatile static PathInterceptorManager instance =null;

    public static PathInterceptorManager getInstance(){
        if (instance == null){
            synchronized (PathInterceptorManager.class){
                if (instance == null){
                    instance = new PathInterceptorManager();
                }
            }
        }
        return instance;
    }
}