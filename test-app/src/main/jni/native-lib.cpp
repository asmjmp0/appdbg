#include <jni.h>
#include <unistd.h>
#include <stdio.h>


extern "C" JNIEXPORT jlong JNICALL
Java_jmp0_test_testapp_TestNative_getNativeLong(
        JNIEnv* env,
        jobject /* this */) {
        return jlong(10);
}

extern "C" JNIEXPORT jstring JNICALL
Java_jmp0_test_testapp_TestNative_testAESFromJava(
        JNIEnv* env,
        jobject /* this */,jstring str) {
        char* c_key = "test_keytest_key";
        char* c_iv = "1234567890123456";
        jstring key = env->NewStringUTF(c_key);
        jstring testIV = env->NewStringUTF(c_iv);
        jclass testAES_clazz = env->FindClass("jmp0/test/testapp/TestAES");
        jmethodID encrypt_AES_method = env->GetStaticMethodID(testAES_clazz,"encrypt_AES","(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;");
        jmethodID decrypt_method = env->GetStaticMethodID(testAES_clazz,"decrypt","(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;");
        jstring encrypted_str =  (jstring) env->CallStaticObjectMethod(testAES_clazz,encrypt_AES_method,key,str,testIV);
        jstring decrypted_str =  (jstring) env->CallStaticObjectMethod(testAES_clazz,decrypt_method,key,encrypted_str,testIV);
        env->DeleteLocalRef(key);
        env->DeleteLocalRef(testIV);
        env->DeleteLocalRef(encrypted_str);
        return decrypted_str;
}