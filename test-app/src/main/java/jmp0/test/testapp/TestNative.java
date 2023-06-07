package jmp0.test.testapp;

import android.util.Log;

import java.util.Arrays;

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/10
 */
public class TestNative {
    static {
        System.loadLibrary("native-lib");
    }

    public void testAll(){
//        getNativeLong();
//        getNativeIntArr();
        Log.d("asmjmp0",AESFromJava("test"));
        detectUnibdg();
    }

    public void getNativeLong(){
        Log.d("jmp0 test",String.valueOf(JNI_LONG()));
    }

    public void getNativeIntArr(){
        Log.d("jmp0 test",Arrays.toString(JNI_INT_ARR()));
    }

    public String AESFromJava(String str){ return testAESFromJava(str);}

    public void detectUnibdg(){
        Log.d("jmp0 test", detectUnibdgNative());
    }

    /**
     * @param str string to encrypt
     * @return encrypted string with base64 encode
     */
    public static native String testAESFromJava(String str);

    public static native long JNI_LONG();
    public static native int[] JNI_INT_ARR();

    public static native String detectUnibdgNative();
}
