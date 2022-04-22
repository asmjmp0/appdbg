package jmp0.test.testapp;

import android.util.Log;

public class TestNativeObject {

    public int testInt = 3;

    public long testLong = 4L;

    public float testFloat = 3.3f;

    public boolean testBoolean = false;

    public char testChar = 'A';

    public double testDouble = 2.2;

    public String testString = "my_test";

    public static String testStaticString = "xxx";

    public TestNativeObject(){
    }

    public void printTestValue(int myInt,String myString){
        Log.d("asmjmp0",myInt+"|"+myString+testString+"|"+testInt);
    }
}
