package com.example.administrator.testsomething.util;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.testsomething.R;
import com.example.administrator.testsomething.util.ItemInfo;

public class SetListLayout extends LinearLayout {
    private Context mContext;
    private LinearLayout mRootView;
    private TextView mPttTv;
    private EditText mPttEdtTxt;
    private Button mPttButton;
    private ItemInfo mItemInfo;

    public SetListLayout(Context context, AttributeSet attrs,ItemInfo itemInfo) {
        super(context, attrs);
        mContext = context;
        mItemInfo = itemInfo;
        mRootView = (LinearLayout) LayoutInflater.from(context)
                .inflate(R.layout.set_list_item, this);
        initView(context);
        initListener();
    }

    public void initView(Context context) {
        mPttTv = (TextView) mRootView.findViewById(R.id.tv_ptt);
        mPttEdtTxt = (EditText) mRootView.findViewById(R.id.et_ptt);
        mPttButton = (Button) mRootView.findViewById(R.id.bt_ptt);

        mPttTv.setText(mItemInfo.getItemName());

    }

    public void initListener() {


        /*mPttEdtTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        mPttButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("gshifeng",mPttEdtTxt.getText().toString());

            }
        });
    }

}
