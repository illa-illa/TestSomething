package com.example.administrator.testsomething.util;

import android.util.Log;

public class IllaLog  {
    private final static String LOG_TAG = "illadebug";

    public static void V(String log){
        Log.v(LOG_TAG,log);
    }
    public static void I(String log){
        Log.i(LOG_TAG,log);
    }
    public static void D(String log){
        Log.d(LOG_TAG,log);
    }
    public static void W(String log){
        Log.w(LOG_TAG,log);
    }
    public static void E(String log){
        Log.d(LOG_TAG,log);
    }
}
