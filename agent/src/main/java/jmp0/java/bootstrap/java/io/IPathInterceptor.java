package jmp0.java.bootstrap.java.io;

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/5/17
 */
public interface IPathInterceptor {
    /**
     * @return path
     */
    String pathFilter(String path);
}
