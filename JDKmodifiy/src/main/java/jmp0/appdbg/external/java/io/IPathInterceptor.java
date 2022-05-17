package jmp0.appdbg.external.java.io;

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/5/17
 */
public interface IPathInterceptor {
    /**
     * @param path can be String or URI
     * @return path
     */
    String pathFilter(Object path);
}
