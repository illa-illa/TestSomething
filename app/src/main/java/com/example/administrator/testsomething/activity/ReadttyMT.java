package com.example.administrator.testsomething.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.GnssStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.OnNmeaMessageListener;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.testsomething.R;
import com.example.administrator.testsomething.util.FileUtils;
import com.example.administrator.testsomething.util.IllaLog;
import com.example.administrator.testsomething.util.PermissionManager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReadttyMT extends Activity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private PermissionManager mPermissionManager;
    private LocationManager mLocationManager = null;

    private boolean mCurrentState = false;
    private boolean mIsStarted = false;

    private final static int  NUMLINE = 150;


    private final static String TTYMT0 = "/dev/ttyMT0";
    private final static String TTYMT1 = "/dev/ttyMT1";
    private final static String ttyGS3 = "/dev/ttyGS3";


    final StringBuffer messagesStringBuffer = new StringBuffer(5);


    private TextView mGpsTV;
    private Button mStartButton;
    private Button mStopButton;
    private ScrollView mScrollView;
    private RadioGroup mRadioGroup;

    private readThread mThread = null;
    private String mDefaultPath = TTYMT0;
    private String mCurrentPath = "";

    private gpsWakeLock mGpsWakeLock;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_gps);

        mGpsWakeLock = new gpsWakeLock();
        mGpsWakeLock.acquireScreenWakeLock(this);
        mGpsWakeLock.acquireCpuWakeLock(this);

        mGpsTV = (TextView) findViewById(R.id.gps);
        mStartButton =(Button)findViewById(R.id.start);
        mStopButton =(Button)findViewById(R.id.stop);
        mStartButton.setOnClickListener(this);
        mStopButton.setOnClickListener(this);
        mStopButton.setEnabled(false);
        mScrollView = (ScrollView)findViewById(R.id.scroll_view);

        mRadioGroup = (RadioGroup)findViewById(R.id.radio);
        mRadioGroup.setOnCheckedChangeListener(this);
        mRadioGroup.check(R.id.ttymt0);

        mPermissionManager = new PermissionManager(this);
        if (mPermissionManager.requestLocationLaunchPermissions()) {
            try {
                mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (mLocationManager != null) {
                    mLocationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, 0, 0, mLocListener);
                    mLocationManager.registerGnssStatusCallback(mGnssStatusCallback);
                    mLocationManager.addNmeaListener(mNmeaListener);
                    if (mLocationManager
                            .isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    } else {
                    }
                }
            } catch (SecurityException e) {
                Toast.makeText(this, "security exception", Toast.LENGTH_LONG)
                        .show();
                IllaLog.D("SecurityException: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                IllaLog.D("IllegalArgumentException: " + e.getMessage());
            }
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (mLocationManager != null) {
                if (mLocationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                } else {
                }

                mLocationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER, 0, 0, mLocListener);
                mLocationManager.registerGnssStatusCallback(mGnssStatusCallback);
            }
        }
        if(mIsStarted) {
            mCurrentState = true;
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mIsStarted) {
            mCurrentState = false;
        }

        if (mLocationManager != null) {
            mLocationManager.removeUpdates(mLocListener);
            mLocationManager.unregisterGnssStatusCallback(mGnssStatusCallback);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        mGpsWakeLock.releaseScreenWakeLock();
        mGpsWakeLock.releaseCpuWakeLock();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(mLocListener);
            mLocationManager.unregisterGnssStatusCallback(mGnssStatusCallback);
            mLocationManager.removeNmeaListener(mNmeaListener);
        }
    }


    private LocationListener mLocListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        // @Override
        public void onLocationChanged(Location location) {
            IllaLog.D("Enter onLocationChanged function");
            Date d = new Date(location.getTime());
            SimpleDateFormat dateFormat = new SimpleDateFormat("z yyyy/MM/dd");
            String date = dateFormat.format(d);

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            String time = timeFormat.format(d);

        }
    };

    private GnssStatus.Callback mGnssStatusCallback = new GnssStatus.Callback() {
        @Override
        public void onStarted() {

        }


        @Override
        public void onStopped() {
            // TODO Auto-generated method stub
        }

        @Override
        public void onSatelliteStatusChanged(GnssStatus status) {

        }
    };


    private OnNmeaMessageListener mNmeaListener = new OnNmeaMessageListener() {
        public void onNmeaMessage(String nmea, long timestamp) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:
                if(!mIsStarted) {
                    mThread = new readThread();
                    mIsStarted = true;
                    mCurrentState = true;
                    mThread.start();
                    mStopButton.setEnabled(true);
                }


                break;
            case R.id.stop:
                if(mIsStarted) {
                    mIsStarted = false;
                    mCurrentState = false;
                    mThread.interrupt();
                    Toast.makeText(this, getResources().getString(R.string.cunc) + "/sdcard/readtytyMT.txt", Toast.LENGTH_LONG).show();
                    mStopButton.setEnabled(false);
                }


                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.ttymt0:
                mCurrentPath = TTYMT0;
                Toast.makeText(this, getResources().getString(R.string.switch_path) + mCurrentPath + getResources().getString(R.string.data), Toast.LENGTH_LONG).show();
                break;
            case R.id.ttymt1:
                mCurrentPath = TTYMT1;
                Toast.makeText(this, getResources().getString(R.string.switch_path) + mCurrentPath + getResources().getString(R.string.data), Toast.LENGTH_LONG).show();
                break;
        }

    }

    class readThread extends Thread {
        @Override
        public void run() {
            IllaLog.D("111111111111111111111111");
            int i = 0;


            List<String> some = new ArrayList<String>();
            String s = "";
            boolean delete = false;

            while (mCurrentState) {
                if(!mCurrentPath.equals("")&&!mCurrentPath.equals(mDefaultPath)) {
                    s = getttyMTValue(mCurrentPath);
                }else {
                    s = getttyMTValue(mDefaultPath);
                }
                if(s.contains("$")){
                    IllaLog.D("----------------------------");
                    s = s.replace("$","\n"+"$");
                }
                IllaLog.D("s  -==============" + s);

                some.add(s);

                if (some.size() > NUMLINE) {
                    some.remove(0);
                }
                messagesStringBuffer.delete(0, messagesStringBuffer.length());
                for (String sss : some) {

                    messagesStringBuffer.append(sss);
                }

                mGpsTV.post(new Runnable() {
                    @Override
                    public void run() {
                        mGpsTV.setText(messagesStringBuffer.toString());
                        mScrollView.fullScroll(130);
                    }
                });
                i++;
                IllaLog.D("getttyMTValue(mCurrentPath) = " + s);
                //IllaLog.D("getTinyCapPID = " + getTinyCapPID());
                FileUtils.writeTxtToFile(s, "/sdcard/", "readtytyMT.txt");
            }


        }
    }

    private String getttyMTValue(String path) {
        try {
            IllaLog.D("22222222222222222222");
            FileReader fr = new FileReader(path);
            IllaLog.D("33333333333333333333");
            BufferedReader br = new BufferedReader(fr);
            IllaLog.D("444444444444444444444  ");
            String readString = br.readLine();

            IllaLog.D("5555555555555555555555 ");
            br.close();
            IllaLog.D("readString= " + readString);
            return readString;
        } catch (IOException e) {
            IllaLog.D("666666666666666666666666 ");
            IllaLog.D("erro= " + android.util.Log.getStackTraceString(e));
        }
        return "";
    }

    public String getTinyCapPID() {

        java.lang.Process psProcess = null;
        try {
            psProcess = Runtime.getRuntime().exec("sh");
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataOutputStream out = new DataOutputStream(psProcess.getOutputStream());
        InputStream is = psProcess.getInputStream();

        try {
            out.writeBytes("cat dev/ttyMT0");
            out.writeBytes("cat dev/ttyMT0\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.writeBytes("exit\n");
            out.flush();
            psProcess.waitFor();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String re = "";
        try {
            if (is.read() != 0) {
                byte firstByte = (byte) is.read();
                int available = is.available();
                byte[] characters = new byte[available + 1];
                characters[0] = firstByte;
                is.read(characters, 1, available);
                re = new String(characters);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return re;
    }

    //打开指定文件，读取其数据，返回字符串对象
    public String readFileData(String fileName) {

        String result = "";

        try {

            FileInputStream fis = this.openFileInput(fileName);

            //获取文件长度
            int lenght = fis.available();

            byte[] buffer = new byte[lenght];

            fis.read(buffer);

            //将byte数组转换成指定格式的字符串
            result = new String(buffer, "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    private class gpsWakeLock {
        private PowerManager.WakeLock mScreenWakeLock = null;
        private PowerManager.WakeLock mCpuWakeLock = null;

        /**
         * Acquire CPU wake lock.
         *
         * @param context
         *            Getting lock context
         */
        void acquireCpuWakeLock(Context context) {
            //Log.v(TAG, "Acquiring cpu wake lock");
            if (mCpuWakeLock != null) {
                return;
            }

            PowerManager pm = (PowerManager) context
                    .getSystemService(Context.POWER_SERVICE);

            mCpuWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK
                    | PowerManager.ACQUIRE_CAUSES_WAKEUP, "myapp:mywakelocktag");
            mCpuWakeLock.acquire();
        }
        void acquireScreenWakeLock(Context context) {
            //Log.v(TAG, "Acquiring screen wake lock");
            if (mScreenWakeLock != null) {
                return;
            }

            PowerManager pm = (PowerManager) context
                    .getSystemService(Context.POWER_SERVICE);

            mScreenWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                    | PowerManager.ACQUIRE_CAUSES_WAKEUP, "myapp:mywakelocktag");
            mScreenWakeLock.acquire();
        }
        void releaseScreenWakeLock() {
            //Log.v(TAG, "Releasing wake lock");

            if (mScreenWakeLock != null) {
                mScreenWakeLock.release();
                mScreenWakeLock = null;
            }
        }

        void releaseCpuWakeLock() {
            //Log.v(TAG, "Releasing cpu wake lock");
            if (mCpuWakeLock != null) {
                mCpuWakeLock.release();
                mCpuWakeLock = null;
            }
        }
    }
}
