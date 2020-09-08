package com.example.administrator.testsomething.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class KeyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("gshifeng","" + intent.getAction());

        intent.getData();

        Log.d("gshifeng","intent.getData() = " + intent.getData());


    }
}
