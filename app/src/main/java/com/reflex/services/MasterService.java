package com.reflex.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.reflex.core.providers.App;

import java.util.List;

/**
 * This service is the root background service for starting
 * app triggers
 */
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
        List<App> apps = AppProvider.getInstance()
                .getAllApps();
        for (App app : apps) {
            app.startTriggers(getApplicationContext());
        }
    }

    private void stopAppsTriggers() {
        List<App> apps = AppProvider.getInstance()
                .getAllApps();
        for (App app : apps) {
            app.stopTriggers(getApplicationContext());
        }
    }
}
