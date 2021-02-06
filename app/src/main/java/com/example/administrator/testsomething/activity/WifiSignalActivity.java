package com.example.administrator.testsomething.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.administrator.testsomething.adapter.wifiListAdapter;

import com.example.administrator.testsomething.R;
import com.example.administrator.testsomething.adapter.ListAdapter;
import com.example.administrator.testsomething.util.IllaLog;
import com.example.administrator.testsomething.util.PermissionManager;
import com.example.administrator.testsomething.util.WifiBean;

import java.util.ArrayList;
import java.util.List;

public class WifiSignalActivity extends Activity implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {
    private ListView mList;
    private wifiListAdapter mWifiListAdapter;
    ArrayList<ScanResult> mScanResultList;
    List<WifiBean> mWifiBeanList;

    private  String mChooseWifiName = "";
    private  int mChooseWifiTime = 10;


    private static PermissionManager mPermissionManager;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_signal);
        //setActionBar();
        mList = (ListView) findViewById(R.id.wifi_list);
        mList.setOnItemClickListener(this);
        mList.setOnScrollListener(this);
        mPermissionManager = new PermissionManager(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPermissionManager.requestLocationLaunchPermissions()) {
            mHandler.post(task);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {


    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private void getInfo() {
        String wifiName;  //wifi名字
        String macAddress;  //mac地址
        int rssi;  //rssi的值
        String apName;  //AP名字
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo();
        wifiManager.startScan();  //开始扫描AP
        mScanResultList = (ArrayList<ScanResult>) wifiManager.getScanResults();
        mWifiBeanList = new ArrayList<>();
        if (mScanResultList.size() == 0) {
            Toast.makeText(this, "当前周围无WiFi", Toast.LENGTH_LONG).show();
        } else {
            IllaLog.D("list.size() == " + mScanResultList.size());
            for (int i = 0; i < mScanResultList.size(); i++) {
                wifiName = mScanResultList.get(i).SSID;

                rssi = mScanResultList.get(i).level;
                macAddress = info.getMacAddress();
                String top = "WiFi名称：" + wifiName + "   RSSI：" + rssi + "";
                String bottom = "MAC地址：" + mScanResultList.get(i).BSSID + "";
                IllaLog.D(top);
                if(wifiName.contains(mChooseWifiName)) {
                    mWifiBeanList.add(new WifiBean(R.drawable.wifi, top, bottom));
                }
            }

            mList.setAdapter(new wifiListAdapter(this, mWifiBeanList));
        }
    }


    private Runnable task = new Runnable() {
        public void run() {
            // TODOAuto-generated method stub
            getInfo();
            mHandler.postDelayed(this, mChooseWifiTime * 1000);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.wifi_settings, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_wifi:
                final EditText inputServer = new EditText(this);
                inputServer.setFocusable(true);
                AlertDialog alertDialognew = new AlertDialog.Builder(this)
                       .setTitle("Choose wifi name")
                        .setView(inputServer)
                       //.setMessage("Please turn on the Bluetooth handset switch")
                       .setPositiveButton(R.string.yes,new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mChooseWifiName = inputServer.getText().toString();
                            }
                        } )
                       .setNegativeButton(R.string.no, null)
                       .create();
                //alertDialognew.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
                alertDialognew.show();
                break;
            case R.id.refresh_time:
                final NumberPicker choosetime = new NumberPicker(this);
                choosetime.setFocusable(true);
                choosetime.setMaxValue(30);
                choosetime.setMinValue(1);
                choosetime.setValue(10);
                AlertDialog alertDialogtime = new AlertDialog.Builder(this)
                        .setTitle("Choose wifi time")
                        .setView(choosetime)
                        //.setMessage("Please turn on the Bluetooth handset switch")
                        .setPositiveButton(R.string.yes,new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mChooseWifiTime = choosetime.getValue();
                            }
                        } )
                        .setNegativeButton(R.string.no, null)
                        .create();
                //alertDialognew.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
                alertDialogtime.show();
                break;

        }
        mHandler.post(task);
        return super.onOptionsItemSelected(item);
    }



}
