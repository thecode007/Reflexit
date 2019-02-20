package com.reflex.services.sms;


import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.reflex.model.ActionBootstrap;
import com.reflex.services.AppProvider;
import com.reflex.core.providers.Trigger;
import com.reflex.core.providers.ReflexProvider;
import com.reflex.core.providers.App;

class SmsReceivedTrigger extends Trigger {

    SmsReceivedTrigger(App app) {
        super("android.provider.Telephony.SMS_RECEIVED", app);

    }

    @Override
    protected void initReceiverBody(Context context,Intent intent) {
        AppProvider appRepository = AppProvider.getInstance();
        for (ActionBootstrap bootstrap : bootstraps) {
            ObjectNode resultCallback = JsonNodeFactory.instance.objectNode();
            app.execute(ReflexProvider.FILTER_SMS_FROM_PROVIDER, intent, bootstrap.getConstraints(), resultCallback);
            Toast.makeText(context, "Result call back: " + resultCallback.toString(), Toast.LENGTH_LONG).show();
            if (resultCallback.get("matched") == null || !resultCallback.get("matched").asBoolean()) {
                continue;
            }
            App targetApp = appRepository.getApp(bootstrap.getApp());
            targetApp.execute(bootstrap.getAction());
        }
    }
}
