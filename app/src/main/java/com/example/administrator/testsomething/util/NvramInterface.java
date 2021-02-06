package com.example.administrator.testsomething.util;


import android.app.NvramManager;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;


public class NvramInterface {

    public static final String TAG = "NvramInterface";

    private static final int MAC_ADDRESS_OFFSET = 0;
    private static final int MAC_ADDRESS_DIGITS = 10;
    private static final String PRODUCT_INFO_FILENAME = "/vendor/nvdata/APCFG/APRDEB/PRODUCT_INFO";

    public static final int key_test = 5;
    public static final int key_ptt = 604;
    public static final int key_camera = 608;
    public static final int key_headset = 612;

    private static final int AP_CFG_REEB_PRODUCT_INFO_LID = 61;

    static public boolean setNv(Context context, int key, String str) {
        NvramManager mNvramManager = (NvramManager) context.getSystemService("NvramService");
        if (mNvramManager == null) {
            return false;
        }
        if (str.length() > (MAC_ADDRESS_OFFSET + MAC_ADDRESS_DIGITS)) {
            return false;
        }

        try {
            int lid = /*getProductInfoLid()*/AP_CFG_REEB_PRODUCT_INFO_LID;
            String buff = mNvramManager.readFileByName(PRODUCT_INFO_FILENAME, MAC_ADDRESS_OFFSET + MAC_ADDRESS_DIGITS);

            if (buff != null) {
                StringBuilder sb = new StringBuilder(buff);
                sb.replace(key, key + 1, str);
                int result = mNvramManager.writeFileByNamevec(PRODUCT_INFO_FILENAME, (MAC_ADDRESS_OFFSET + MAC_ADDRESS_DIGITS), sb.toString());
                //Log.d(TAG, "write result is " +result);
                if (result == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "logstart:\n" + e.getMessage());
        }

        return false;
    }


    static public int getNv(Context context, int key) {
        NvramManager mNvramManager = (NvramManager) context.getSystemService("NvramService");
        if (mNvramManager == null) {
            return -1;
        }

        try {
            int lid = /*getProductInfoLid()*/AP_CFG_REEB_PRODUCT_INFO_LID;
            String buff = mNvramManager.readFileByName(PRODUCT_INFO_FILENAME, MAC_ADDRESS_OFFSET + MAC_ADDRESS_DIGITS);
            Log.d(TAG, "buff is " + buff);
            if (buff != null) {
                return Integer.parseInt(buff.substring(key * 2 + 1, key * 2 + 2));
            }
        } catch (Exception e) {
            Log.d(TAG, "logstart:" + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    public static byte[] hexStringToByteArray(String hexString) {
        int length = hexString.length();
        byte[] buffer = new byte[length / 2];

        for (int i = 0; i < length; i += 2) {
            buffer[i / 2] = (byte) ((toByte(hexString.charAt(i)) << 4) | toByte(hexString.charAt(i + 1)));
        }

        return buffer;
    }

    private static int toByte(char c) {
        if (c >= '0' && c <= '9') return (c - '0');
        if (c >= 'A' && c <= 'F') return (c - 'A' + 10);
        if (c >= 'a' && c <= 'f') return (c - 'a' + 10);

        throw new RuntimeException("Invalid hex char '" + c + "'");
    }
}