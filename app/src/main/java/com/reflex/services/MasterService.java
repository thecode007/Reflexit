package com.reflex.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.reflex.services.sms.SmsReceivedTrigger;

public class MasterService extends Service {

    SmsReceivedTrigger receivedTrigger;
    public MasterService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.wtf("Service","Master started");
        receivedTrigger = new SmsReceivedTrigger(getApplicationContext());
        receivedTrigger.register();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        receivedTrigger.unRegister();
        Log.wtf("Service","Master destroyed");
        Toast.makeText(getApplicationContext(), "destroyed",Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}
