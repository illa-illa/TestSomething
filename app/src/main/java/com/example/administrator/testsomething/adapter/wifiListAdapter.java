package com.example.administrator.testsomething.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.testsomething.R;
import com.example.administrator.testsomething.util.ItemInfo;
import com.example.administrator.testsomething.util.WifiBean;

import java.util.ArrayList;
import java.util.List;

public class wifiListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<WifiBean> mList;
    public wifiListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }
    public wifiListAdapter(Context context, List<WifiBean> list) {
        this.mInflater = LayoutInflater.from(context);
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = mInflater.inflate(R.layout.signal_list, parent, false);

        ImageView imageView = (ImageView)convertView.findViewById(R.id.item_image);
        TextView topText = (TextView) convertView.findViewById(R.id.tv_one);
        TextView bottomText = (TextView) convertView.findViewById(R.id.tv_two);
        WifiBean wifiBean = mList.get(position);
        imageView.setImageResource(wifiBean.ImageId);
        topText.setText(wifiBean.Top);
        bottomText.setText(wifiBean.Bottom);
        return convertView;
    }
}
