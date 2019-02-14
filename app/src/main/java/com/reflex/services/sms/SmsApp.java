package com.reflex.services.sms;

import android.support.annotation.Nullable;

import com.reflex.services.providers.ActionRepository;
import com.reflex.services.providers.App;
import com.reflex.services.providers.TriggerRepository;

public class SmsApp extends App {

    public static SmsApp instance;

    public static SmsApp getInstance() {
        if (instance == null) {
            instance = new SmsApp();
        }
        return instance;
    }

    private SmsApp() {
        super(SmsTriggerRepository.getInstance(),SmsActions.getInstance());
    }
}
