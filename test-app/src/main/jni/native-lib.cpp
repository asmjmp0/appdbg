#include <jni.h>
#include <unistd.h>
#include <stdio.h>


extern "C" JNIEXPORT jlong JNICALL
Java_jmp0_test_testapp_TestNative_getNativeLong(
        JNIEnv* env,
        jobject /* this */) {
        return jlong(10);
}