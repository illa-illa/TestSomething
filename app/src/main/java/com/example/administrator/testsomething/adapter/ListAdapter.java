package com.example.administrator.testsomething.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.testsomething.R;
import com.example.administrator.testsomething.util.ItemInfo;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<ItemInfo> mList;

    public ListAdapter(Context context, ArrayList<ItemInfo> list) {
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
            convertView = mInflater.inflate(R.layout.list_item, parent, false);

        TextView number = convertView.findViewById(R.id.item_number);
        TextView text = convertView.findViewById(R.id.item_text);

        number.setText(String.valueOf(position + 1));
        text.setText(mList.get(position).getItemName());

        return convertView;
    }
}
