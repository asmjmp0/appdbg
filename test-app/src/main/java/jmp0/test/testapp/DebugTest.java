package jmp0.test.testapp;

import android.util.Log;

public class DebugTest {
    private int just_int = 1;
    private String ss;
    private String nullPtr = null;

    public DebugTest(int a,String b){
        this.just_int = a;
        String temp = b + "111";
        this.ss = temp + "22";
    }

    private void tryTest(String sss){
        try {
            String tt = sss + this.ss;
            tt = nullPtr.split(" ") + tt;
        } catch (Exception e) {
            int a = just_int + 10;
            String temp = sss + "11";
            Log.d("asmjmp0",e.getMessage()+temp);
        }
    }

    public void testAll(int mode){
        if(mode == 1){
            String in = "10" + mode;
            tryTest(in);
        }
    }
}
