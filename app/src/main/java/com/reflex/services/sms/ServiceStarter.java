package com.reflex.services.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Built for listening to the start signal of the os
 * inorder to start the main service on boot up
 */
public class ServiceStarter extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
