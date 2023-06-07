#include <jni.h>
#include <unistd.h>
#include <stdio.h>
#include <malloc.h>
#include <android/log.h>
#define KLOG_TAG "asmjmp0"

#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, KLOG_TAG, __VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, KLOG_TAG, __VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN, KLOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, KLOG_TAG, __VA_ARGS__)
#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, KLOG_TAG, __VA_ARGS__)


extern "C" JNIEXPORT jlong JNICALL
Java_jmp0_test_testapp_TestNative_getNativeLong(
        JNIEnv* env,
        jobject /* this */) {
        return jlong(10);
}

extern "C" JNIEXPORT jstring JNICALL
Java_jmp0_test_testapp_TestNative_detectUnibdgNative(
        JNIEnv* env,
        jobject /* this */) {
        jboolean copy;
        jstring str = env->NewStringUTF("1");
        unsigned long p1 = (unsigned long)(void*)env->GetStringUTFChars(str,&copy);
        unsigned long p2 = (unsigned long)malloc(1);
        env->ReleaseStringUTFChars(str,(char *)p1);
        env->DeleteLocalRef(str);
        size_t page_size = getpagesize();
        size_t sub;
        if(p2>p1) sub = p2 - p1;
        else sub = p1 -p2;
        bool condition= sub > page_size;
        char buf[30];
        sprintf(buf,"unidbg:%d,sub:%ld",condition,sub);
        return env->NewStringUTF(buf);
}

extern "C" JNIEXPORT jstring JNICALL
Java_jmp0_test_testapp_TestNative_testAESFromJava(
        JNIEnv* env,
        jobject /* this */,jstring str) {
        //test call static method
        char* c_key = "test_keytest_key";
        char* c_iv = "1234567890123456";
        jstring key = env->NewStringUTF(c_key);
        jstring testIV = env->NewStringUTF(c_iv);
        jclass testAES_clazz = env->FindClass("jmp0/test/testapp/TestAES");
        jmethodID encrypt_AES_method = env->GetStaticMethodID(testAES_clazz,"encrypt_AES","(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;");
        jmethodID decrypt_method = env->GetStaticMethodID(testAES_clazz,"decrypt","(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;");
        jstring encrypted_str =  (jstring) env->CallStaticObjectMethod(testAES_clazz,encrypt_AES_method,key,str,testIV);
        jstring decrypted_str =  (jstring) env->CallStaticObjectMethod(testAES_clazz,decrypt_method,key,encrypted_str,testIV);
        //test call member method
        jclass TestNativeObject_clazz = env->FindClass("jmp0/test/testapp/TestNativeObject");
        jmethodID new_method = env->GetMethodID(TestNativeObject_clazz,"<init>","()V");
        jobject TestNativeObject_obj = env->NewObject(TestNativeObject_clazz,new_method);
        jmethodID print_method = env->GetMethodID(TestNativeObject_clazz,"printTestValue","(ILjava/lang/String;)V");
        env->CallVoidMethod(TestNativeObject_obj,print_method,1234,key);
        //test set and get field "testString"
        jfieldID testString = env->GetFieldID(TestNativeObject_clazz,"testString","Ljava/lang/String;");
        env->SetObjectField(TestNativeObject_obj,testString,key);
        env->GetObjectField(TestNativeObject_obj,testString);

        //test set and get field "testInt"
        jfieldID testInt = env->GetFieldID(TestNativeObject_clazz,"testInt","I");
        env->SetIntField(TestNativeObject_obj,testInt,11);
        env->GetIntField(TestNativeObject_obj,testInt);

        //test set and get staic field
        jfieldID testStaticString = env->GetStaticFieldID(TestNativeObject_clazz,"testStaticString","Ljava/lang/String;");
        env->SetStaticObjectField(TestNativeObject_clazz,testStaticString,key);
        env->GetStaticObjectField(TestNativeObject_clazz,testStaticString);


        env->DeleteLocalRef(key);
        env->DeleteLocalRef(testIV);
        env->DeleteLocalRef(encrypted_str);
        return decrypted_str;
}