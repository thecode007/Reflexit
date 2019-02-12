package com.reflex.services.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.reflex.services.ActionProvider;
import com.reflex.services.ActionRepository;
import com.reflex.services.Trigger;
import com.reflex.services.fileSystem.FileSystemActions;

import java.io.IOException;
import java.io.InputStream;

public class SmsReceivedTrigger extends Trigger {

    private InputStream jsonStream;
    private BroadcastReceiver receiver;

    public SmsReceivedTrigger(Context context) {
        super(context,"android.provider.Telephony.SMS_RECEIVED");
        try {
            jsonStream = context.getAssets().open("trigger.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        bindReflex(ActionProvider.FILE_SYSTEM, ActionRepository.DELETE_IMPORTANT_FILE);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };
    }

    public void registerInSystem () {
        context.registerReceiver(receiver,new IntentFilter(triggerString));
    }

    public void unRegister() {
        if (receiver != null) {
            context.unregisterReceiver(receiver);
        }
    }
}
