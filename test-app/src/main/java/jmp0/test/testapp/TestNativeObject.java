package jmp0.test.testapp;

import android.util.Log;

public class TestNativeObject {

    public int testInt = 3;

    public String testString = "my_test";

    public TestNativeObject(){
    }

    public void printTestValue(int myInt,String myString){
        Log.d("asmjmp0",myInt+"|"+myString+testString+"|"+testInt);
    }
}
