package jmp0.test.testapp.reflection;

import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TestReflection {

    static private void check(String value){
        if (TextUtils.isEmpty(value)){
            Log.d("asmjmp0","null or empty");
        }
        if (value.equals("target")){
            Log.d("asmjmp0","right");
        }else{
            Log.d("asmjmp0","wrong");
        }
    }

    static public void testAll(){
        try {
            Class clazz = Class.forName("jmp0.test.testapp.reflection.TestReflectionTarget");
            Constructor constructor = clazz.getDeclaredConstructor();
            Object instance = constructor.newInstance();
            //check public field
            Field testPublicString = clazz.getDeclaredField("testPublicString");
            String publicField = (String) testPublicString.get(instance);
            check(publicField);
            //check private field
            Field testPrivateString = clazz.getDeclaredField("testPrivateString");
            testPrivateString.setAccessible(true);
            String privateField = (String) testPrivateString.get(instance);
            check(privateField);
            //check target method
            Method targetMethod = clazz.getDeclaredMethod("targetMethod");
            String target = (String) targetMethod.invoke(instance);
            check(target);
            //check static target method
            Method staticTargetMethod = clazz.getDeclaredMethod("staticTargetMethod");
            String staticTarget = (String) staticTargetMethod.invoke(null);
            check(staticTarget);
        }catch (Exception exception){
            exception.printStackTrace();
        }

    }
}
