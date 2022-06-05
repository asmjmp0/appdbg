package jmp0.test.testapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreferencesTest {
    private Context context;
    public SharedPreferencesTest(Context context){
        this.context = context;
    }

    public void testWrite(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("test1", "agfrwx");
        editor.putInt("test2", 23231);
        editor.apply();
    }

    public void testRead(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE);
        String str = sharedPreferences.getString("test1","default");
        int i = sharedPreferences.getInt("test2",-1);
        Log.d("asmjmp0","get test1:"+str);
        Log.d("asmjmp0","get test1:"+i);

    }

    public void testAll(){
        testWrite();
        testRead();
    }
}
