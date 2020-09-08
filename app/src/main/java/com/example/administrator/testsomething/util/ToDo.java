package com.example.administrator.testsomething.util;

import android.app.Activity;
import android.content.Context;

public class ToDo {


    public static long getBatteryCapacity_PowerProfile(Activity activity) {
        Object newInstance;
        String str = "com.android.internal.os.PowerProfile";
        try {
            newInstance = Class.forName(str).getConstructor(new Class[]{Context.class}).newInstance(new Object[]{activity});
        } catch (Exception unused) {
            newInstance = null;
        }
        try {
            return Math.round(((Double) Class.forName(str).getMethod("getAveragePower", new Class[]{String.class}).invoke(newInstance, new Object[]{"battery.capacity"})).doubleValue());
        } catch (Exception unused2) {
            return 0;
        }
    }


}