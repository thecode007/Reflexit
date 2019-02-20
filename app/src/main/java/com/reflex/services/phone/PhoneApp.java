package com.reflex.services.phone;

import com.reflex.core.providers.App;

public class PhoneApp extends App {

    public static PhoneApp instance;

    public static PhoneApp getInstance() {
        if (instance == null) {
            instance = new PhoneApp();
        }
        return instance;
    }


//    private SmsApp() {
//        super(new TriggerProvider() {
//        }, SmsActions.getInstance());
//        triggerProvider.register(new SmsReceivedTrigger(this));
//    }
}
