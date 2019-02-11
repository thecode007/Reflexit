package com.reflex.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.reflex.services.sms.SmsReceiver;
import com.reflex.services.sms.SmsReflexInitializer;

public class MasterService extends Service {

    private BroadcastReceiver smsReceiver;
    public MasterService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Service","Master");
        SmsReflexInitializer.init();
        smsReceiver = new SmsReceiver();
        registerReceiver(smsReceiver,new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "destroyed",Toast.LENGTH_SHORT).show();
        unregisterReceiver(smsReceiver);
    }
}
