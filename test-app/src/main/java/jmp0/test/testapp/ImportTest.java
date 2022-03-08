package jmp0.test.testapp;

import android.content.Context;
import android.provider.Settings;
import android.util.ArrayMap;

import javax.sql.StatementEvent;

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/8
 */
public class ImportTest {
    public ImportTest(){
        getStr("ajdjw");
        System.err.println("xxxx");
    }

    private void getStr(String s){
        System.out.println(String.valueOf(System.currentTimeMillis())+"xxxxx");
    }
}
