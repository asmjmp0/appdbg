package jmp0.test.testapp;

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/10
 */
public class TestNative {
    public long getNativeLong(){
        return JNI_LONG();
    }

    public static native long JNI_LONG();
}
