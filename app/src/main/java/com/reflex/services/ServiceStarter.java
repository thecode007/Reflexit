package com.reflex.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Built for listening to the start signal of the os
 * inorder to start the main service on boot up
 */
public class ServiceStarter extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context,"Service Starter booted", Toast.LENGTH_LONG).show();

        if( Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            context.startService(new Intent(context, MasterService.class));
        }


    }
}
