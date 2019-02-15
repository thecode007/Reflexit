package com.reflex.services.sms;


import android.content.Context;
import android.content.Intent;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.reflex.model.ActionBootstrap;
import com.reflex.services.AppRepository;
import com.reflex.services.Trigger;
import com.reflex.services.providers.ActionRepository;
import com.reflex.services.providers.App;

public class SmsReceivedTrigger extends Trigger {

    public SmsReceivedTrigger(Context context) {
        super(context,"android.provider.Telephony.SMS_RECEIVED",
                AppRepository.getInstance().
                        getApp(App.SMS));

    }

    @Override
    protected void initReceiverBody(Context context, Intent intent) {
        AppRepository appRepository = AppRepository.getInstance();
        for (ActionBootstrap bootstrap : bootstraps) {
            ObjectNode resultCallback = bootstrap.getConstraints();
            app.execute(ActionRepository.FILTER_SMS_FROM_PROVIDER, intent, bootstrap.getConstraints(), resultCallback);
            if (resultCallback.get("matched") == null || !resultCallback.get("matched").asBoolean()) {
                continue;
            }
            App targetApp = appRepository.getApp(bootstrap.getApp());
            targetApp.execute(bootstrap.getAction());
        }
    }
}
