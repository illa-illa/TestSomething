package com.example.administrator.testsomething.util;

import android.content.Context;

public class ItemInfo {
    private Integer mNameId;
    private int mKey;
    private Class<?> mCls;
    private Context mContext;

    public ItemInfo(Integer nameId, int nvKey, Class<?> cls, Context context) {
        mNameId = nameId;
        mKey = nvKey;
        mContext = context;
        mCls = cls;
    }

    public Integer getItemName() {
        return mNameId;
    }

    public int getItemKey() {
        return mKey;
    }

    public Class<?> getItemClass() {
        return mCls;
    }


}
