package com.reflex.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.reflex.services.providers.App;
import com.reflex.services.sms.SmsReceivedTrigger;

import java.util.List;

public class MasterService extends Service {

    public MasterService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startAppsTriggers();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopAppsTriggers();
    }

    private void startAppsTriggers() {
        List<App> apps = AppRepository.getInstance()
                .getAllApps();
        for (App app : apps) {
            app.startTriggers(getApplicationContext());
        }
    }

    private void stopAppsTriggers() {
        List<App> apps = AppRepository.getInstance()
                .getAllApps();
        for (App app : apps) {
            app.stopTriggers(getApplicationContext());
        }
    }
}
