package com.example.administrator.testsomething.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.example.administrator.testsomething.receiver.KeyBroadcastReceiver;

public class TestBrocastReceiverActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //调用函数打印包名类名
        sendBroadcastReceiver("");
    }

    public void registerReceiver() {
        KeyBroadcastReceiver sOnBroadcastReciver = new KeyBroadcastReceiver();
        IntentFilter recevierFilter = new IntentFilter();
        //recevierFilter.addAction("com.android.intent.ptt.down");
        registerReceiver(sOnBroadcastReciver, recevierFilter);
        //       Intent
    }

    public void sendBroadcastReceiver(String action){
        Intent intent = new Intent();
        intent.setAction(action);
        //intent.addFlags(0x01000000);
        this.sendBroadcast(intent);
    }
}
