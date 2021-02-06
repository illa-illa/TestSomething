package com.example.administrator.testsomething.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.location.GnssStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.OnNmeaMessageListener;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.testsomething.R;
import com.example.administrator.testsomething.adapter.ListAdapter;
import com.example.administrator.testsomething.constant.IllaConstant;
import com.example.administrator.testsomething.util.FileUtils;
import com.example.administrator.testsomething.util.IllaLog;
import com.example.administrator.testsomething.util.ItemInfo;
import com.example.administrator.testsomething.util.PermissionManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {
    private ArrayList<ItemInfo> mItemsList;

    private ListAdapter mAdapter;
    private ListView mList;

    private int mFirstVisibleItem;

    private static PermissionManager mPermissionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addView();
        mList = (ListView) findViewById(R.id.list);
        mList.setOnItemClickListener(this);
        mList.setOnScrollListener(this);

        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(0, cameraInfo);
        IllaLog.D("cameraInfo.facing = " + cameraInfo.facing);
        //illa add
        BluetoothConnectionReceiver audioNoisyReceiver = new BluetoothConnectionReceiver();
        IntentFilter audioFilter = new IntentFilter();
        audioFilter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
        audioFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(audioNoisyReceiver, audioFilter);
        //illa end

        //intent.addFlags(0x01000000);
        //startActivity(intent);

        //PackageManager.FEATURE_WIFI;
//        CameraCharacteristics c = mAllStaticInfo.get(id).getCharacteristics();
//        StreamConfigurationMap config =
//                c.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
//        Size[] yuvSizes = config.getOutputSizes(ImageFormat.YUV_420_888);
//        ArrayList<Size> yuvSizesList = new ArrayList<>(Arrays.asList(yuvSizes));

        AudioManager localAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        boolean isBluetoothA2dpOn = localAudioManager.isBluetoothA2dpOn();
        IllaLog.D("isBluetoothScoOn = " + localAudioManager.isBluetoothScoOn());
        IllaLog.D("isBluetoothA2dpOn = " + isBluetoothA2dpOn);
        Toast.makeText(this,"",Toast.LENGTH_LONG).show();

        mPermissionManager = new PermissionManager(this);


    }
    public class BluetoothConnectionReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE, -1);
            android.util.Log.e("illadebug", "state111111111111 == " + state);

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        mAdapter = new ListAdapter(this, mItemsList);
        mList.setAdapter(mAdapter);
        mList.setSelection(mFirstVisibleItem);

        //IllaLog.D(getIMSI(this));
    }

    public void addView() {
        mItemsList = new ArrayList<ItemInfo>();
        //if(mItemsList.size() < 0);
        //ItemInfo ItemInfo = mItemsList.get(0) ;
        mItemsList.add(new ItemInfo(R.string.key, 0, KeyListActivity.class, this));
        mItemsList.add(new ItemInfo(R.string.test_broadcast, 0, TestBrocastReceiverActivity.class, this));
        mItemsList.add(new ItemInfo(R.string.print_pkg_cls, 0, PrintPkgAndClsActivity.class, this));
        mItemsList.add(new ItemInfo(R.string.test_gps, 0, ReadttyMT.class, this));
        mItemsList.add(new ItemInfo(R.string.test_wifi, 0, WifiSignalActivity.class, this));
        mItemsList.add(new ItemInfo(R.string.set_nvram, 0, SetNvRamActivity.class, this));
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //IllaLog.D("keyCode ==  " + keyCode);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        Context context = getApplicationContext();
        intent.setClass(context, mItemsList.get(position).getItemClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(IllaConstant.NAME, position);
        context.startActivity(intent);
        //1111

    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // TODO Auto-generated method stub
        mFirstVisibleItem = firstVisibleItem;
    }

    public static String getIMSI(Context context) {
        if (mPermissionManager.requestLocationLaunchPermissions()) {
            try {
                try {
                    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    //获取IMSI号

                    String imsi = telephonyManager.getSubscriberId();
                    if (null == imsi) {
                        imsi = "";
                    }
                    return imsi;
                } catch (Exception e) {
                    e.printStackTrace();
                    return "";
                }


            } catch (SecurityException e) {
                Toast.makeText(context, "security exception", Toast.LENGTH_LONG)
                        .show();
                IllaLog.D("SecurityException: " + e.getMessage());
                return "";
            } catch (IllegalArgumentException e) {
                IllaLog.D("IllegalArgumentException: " + e.getMessage());
                return "";
            }
        }
        return "";

    }

}
