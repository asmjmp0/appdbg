package jmp0.test.testapp;

import android.util.Log;

import java.util.Arrays;

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/10
 */
public class TestNative {

    public void testAll(){
        getNativeLong();
        getNativeIntArr();
    }

    public void getNativeLong(){
        Log.d("jmp0 test",String.valueOf(JNI_LONG()));
    }

    public void getNativeIntArr(){
        Log.d("jmp0 test",Arrays.toString(JNI_INT_ARR()));
    }
    public static native long JNI_LONG();
    public static native int[] JNI_INT_ARR();
}
