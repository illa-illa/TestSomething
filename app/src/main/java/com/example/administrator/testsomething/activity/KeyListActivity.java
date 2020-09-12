package com.example.administrator.testsomething.activity;

import android.app.Activity;
import android.content.Context;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Bundle;

import com.example.administrator.testsomething.util.IllaLog;

public class KeyListActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        audioManager.getMode();
        //audioManager.setBluetoothA2dpOn(true);
        //audioManager.setBluetoothScoOn(false);
        //audioManager.setSpeakerphoneOn(true);
        IllaLog.D(" getMode = " +  audioManager.getMode());
        IllaLog.D(" isBluetoothA2dpOn = " +  audioManager.isBluetoothA2dpOn());
        IllaLog.D(" isBluetoothScoOn = " +  audioManager.isBluetoothScoOn());
        IllaLog.D(" isSpeakerphoneOn = " +  audioManager.isSpeakerphoneOn());
        AudioDeviceInfo[] addedDevices = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS);
        for(AudioDeviceInfo audioDeviceInfo : addedDevices){
            IllaLog.D(audioDeviceInfo.getAddress());
        }
        AudioDeviceInfo[] addedDevicess = audioManager.getDevices(AudioManager.GET_DEVICES_ALL);
        for(AudioDeviceInfo audioDeviceInfo : addedDevicess){
            IllaLog.D("GET_DEVICES_ALL" + audioDeviceInfo.getAddress());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
//        audioManager.getMode();
//        audioManager.setSpeakerphoneOn(false);
//        audioManager.startBluetoothSco();
//        audioManager.setBluetoothScoOn(true);
//        IllaLog.D(" getMode = " + + audioManager.getMode());
    }
}
