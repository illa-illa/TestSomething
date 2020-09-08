package com.example.administrator.testsomething.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.testsomething.util.IllaLog;

import java.util.Collections;
import java.util.List;

public class PrintPkgAndClsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        plintPkgAndCls(getResolveInfos());
    }

    private List<ResolveInfo> getResolveInfos() {
        List<ResolveInfo> appList = null;

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = getPackageManager();
        appList = pm.queryIntentActivities(intent, 0);
        Collections.sort(appList, new ResolveInfo.DisplayNameComparator(pm));

        return appList;

    }


    private void plintPkgAndCls(List<ResolveInfo> resolveInfos) {
        PackageManager packageManager = getPackageManager();

        IllaLog.D("####################start######################");
        for (int i = 0; i < resolveInfos.size(); i++) {
            String pkg = resolveInfos.get(i).activityInfo.packageName;
            String cls = resolveInfos.get(i).activityInfo.name;
            String title = null;

            try {
                ApplicationInfo applicationInfo = packageManager.getPackageInfo(pkg, i).applicationInfo;
                title = applicationInfo.loadLabel(packageManager).toString();
            } catch (Exception e) {

            }


            IllaLog.D(title + "：" + pkg + "/" + cls);
        }
        IllaLog.D("#####################end#######################");
    }

}
