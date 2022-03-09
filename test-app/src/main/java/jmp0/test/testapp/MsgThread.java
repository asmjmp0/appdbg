package jmp0.test.testapp;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MsgThread extends Thread{

    public static final String TAG = "MsgThread";

    public Handler _handler = null;

    @Override
    public void run() {
        Log.d(TAG, "进入Thread的run");

        Looper.prepare();

//        _handler = new Handler(Looper.getMainLooper()){
        _handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(Message msg){
                Log.d(TAG, "获得了message");
                super.handleMessage(msg);
            }
        };
        Looper.loop();
    }

    public void sendMsg(int what, Object object){
        Message message = _handler.obtainMessage();
        message.what = what;
        message.obj = object;
        _handler.sendMessage(message);
    }
}