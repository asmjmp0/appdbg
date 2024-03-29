package jmp0.java.bootstrap.java.io;

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/5/17
 */
public final class PathInterceptorManager {
    private PathInterceptorManager(){ }

    public volatile IPathInterceptor nameInterceptor = null;

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
