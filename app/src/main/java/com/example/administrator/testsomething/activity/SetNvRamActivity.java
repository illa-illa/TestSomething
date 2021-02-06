package com.example.administrator.testsomething.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.telecom.TelecomManager;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administrator.testsomething.R;
import com.example.administrator.testsomething.util.IllaLog;
import com.example.administrator.testsomething.util.NvramInterface;

public class SetNvRamActivity extends PreferenceActivity implements Preference.OnPreferenceClickListener , RadioGroup.OnCheckedChangeListener {
    private static final String PTT_KEY = "ptt_key_broadcast";

    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_nvram);
        //addPreferencesFromResource(R.xml.key_list);
        createPreference();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void createPreference() {
        PreferenceScreen preferenceScreen = this.getPreferenceManager().createPreferenceScreen(this);
        this.setPreferenceScreen(preferenceScreen);
        Preference pttPreference = new Preference(this);
        pttPreference.setKey(PTT_KEY);
        pttPreference.setTitle(R.string.ptt_button);
        pttPreference.setSummary(R.string.key_summary);
        pttPreference.setOnPreferenceClickListener(this);
        preferenceScreen.addPreference(pttPreference);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()){
            case PTT_KEY:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                final AlertDialog dialog = builder.create();
                View view = View.inflate(this, R.layout.ptt_broadcast_list, null);
                dialog.setView(view);
                mRadioGroup = (RadioGroup)view.findViewById(R.id.ptt_radio);
                mRadioGroup.setOnCheckedChangeListener(this);
                IllaLog.D(NvramInterface.getNv(this,NvramInterface.key_ptt)+ "");
                IllaLog.D("NvramInterface.getNv(this,NvramInterface.key_ptt) = " + NvramInterface.getNv(this,NvramInterface.key_ptt));
                if(NvramInterface.getNv(this,NvramInterface.key_ptt) == 0) {
                    mRadioGroup.check(R.id.ptt_radiobutton_one);
                }else {
                    mRadioGroup.check(R.id.ptt_radiobutton_two);
                }
                dialog.show();
                break;
        }
        return true;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.ptt_radiobutton_one:

                NvramInterface.setNv(this,NvramInterface.key_ptt,"0");
                Toast.makeText(this,NvramInterface.getNv(this,NvramInterface.key_ptt)+"",Toast.LENGTH_LONG).show();
                break;
            case R.id.ptt_radiobutton_two:
                IllaLog.D("2222222222222222");
                NvramInterface.setNv(this,NvramInterface.key_ptt,"1");
                Toast.makeText(this,NvramInterface.getNv(this,NvramInterface.key_ptt)+"",Toast.LENGTH_LONG).show();
                break;
                //test

        }
    }
}
