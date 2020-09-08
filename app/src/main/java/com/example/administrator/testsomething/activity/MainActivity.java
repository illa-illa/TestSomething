package com.example.administrator.testsomething.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.testsomething.R;
import com.example.administrator.testsomething.adapter.ListAdapter;
import com.example.administrator.testsomething.constant.IllaConstant;
import com.example.administrator.testsomething.util.IllaLog;
import com.example.administrator.testsomething.util.ItemInfo;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {
    private ArrayList<ItemInfo> mItemsList;

    private ListAdapter mAdapter;
    private ListView mList;

    private int mFirstVisibleItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addView();
        mList = (ListView) findViewById(R.id.list);
        mList.setOnItemClickListener(this);
        mList.setOnScrollListener(this);

        Intent intent = new Intent();
        intent.setAction("com.test.bro");
        intent.setPackage("com.example.administrator.testsomething");
        //intent.addFlags(0x01000000);
        this.sendBroadcast(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter = new ListAdapter(this, mItemsList);
        mList.setAdapter(mAdapter);
        mList.setSelection(mFirstVisibleItem);


    }

        public void addView () {
            mItemsList = new ArrayList<ItemInfo>();
            //if(mItemsList.size() < 0);
            //ItemInfo ItemInfo = mItemsList.get(0) ;
            mItemsList.add(new ItemInfo(R.string.key, 0, KeyListActivity.class, this));
            mItemsList.add(new ItemInfo(R.string.test_broadcast, 0, TestBrocastReceiverActivity.class, this));
            mItemsList.add(new ItemInfo(R.string.print_pkg_cls, 0, PrintPkgAndClsActivity.class, this));
        }


        @Override
        public boolean onKeyDown ( int keyCode, KeyEvent event){
            IllaLog.D("keyCode ==  " + keyCode);
            return super.onKeyDown(keyCode, event);
        }

        @Override
        public void onItemClick (AdapterView < ? > parent, View view,int position,
        long id){
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            Context context = getApplicationContext();
            intent.setClass(context, mItemsList.get(position).getItemClass());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(IllaConstant.NAME, position);
            context.startActivity(intent);
        }


        @Override
        public void onScrollStateChanged (AbsListView view,int scrollState){
            // TODO Auto-generated method stub

        }

        @Override
        public void onScroll (AbsListView view,int firstVisibleItem,
        int visibleItemCount, int totalItemCount){
            // TODO Auto-generated method stub
            mFirstVisibleItem = firstVisibleItem;
        }


    }
