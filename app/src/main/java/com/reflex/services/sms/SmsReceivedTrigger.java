package com.reflex.services.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import com.reflex.services.providers.ActionProvider;
import com.reflex.services.providers.ActionRepository;
import com.reflex.services.Trigger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class SmsReceivedTrigger extends Trigger {



    public SmsReceivedTrigger(Context context) {
        super(context,"android.provider.Telephony.SMS_RECEIVED");
        bindReflex(ActionProvider.FILE_SYSTEM, ActionRepository.DELETE_IMPORTANT_FILE);
        try {
            filterFields.put("number","");
            filterFields.put("message","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initReceiver();
    }



    @Override
    protected void initReceiver() {

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };
    }


}
