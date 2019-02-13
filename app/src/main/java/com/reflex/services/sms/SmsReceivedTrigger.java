package com.reflex.services.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import com.reflex.services.ActionProvider;
import com.reflex.services.ActionRepository;
import com.reflex.services.Trigger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class SmsReceivedTrigger extends Trigger {

    private BroadcastReceiver receiver;
    private Context context;

    public SmsReceivedTrigger(Context context) {
        super(context,"android.provider.Telephony.SMS_RECEIVED");
        this.context = context;
        bindReflex(ActionProvider.FILE_SYSTEM, ActionRepository.DELETE_IMPORTANT_FILE);
        initReceiver();
        try {
            filterFields.put("number","");
            filterFields.put("message","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void initReceiver() {

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    JSONObject resultCallBack = new JSONObject();
                    InputStream i = context.getAssets().open("bootstrap-trigger.json");

                    SmsActions.getInstance()
                            .execute(ActionRepository.READ_SMS_FROM_PROVIDER,
                                    intent, resultCallBack);
                    Toast.makeText(context, resultCallBack.toString(), Toast.LENGTH_LONG).show();

                } catch (NullPointerException | IOException e) {
                    e.printStackTrace();
                }
//                catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        };
    }

    public void register () {
        context.registerReceiver(receiver,new IntentFilter(triggerString));
    }

    public void unRegister() {
        if (receiver != null) {
            context.unregisterReceiver(receiver);
        }
    }
}
