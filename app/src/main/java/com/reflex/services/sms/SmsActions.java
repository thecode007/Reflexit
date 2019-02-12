package com.reflex.services.sms;

import com.reflex.services.ActionRepository;


public class SmsActions extends ActionRepository {

    public static ActionRepository getInstance() {
        if (instance == null) {
            instance = new SmsActions();
        }
        return instance;
    }


}
