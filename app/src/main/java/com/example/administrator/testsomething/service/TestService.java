package com.example.administrator.testsomething.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TestService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //startForeground();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
