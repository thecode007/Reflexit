package com.reflex.services.os;

import com.reflex.R;
import com.reflex.core.model.App;
import com.reflex.core.providers.TriggerProvider;

public class OS extends App {

    public static OS instance;

    public static OS getInstance() {
        if (instance == null) {
            instance = new OS();
        }
        return instance;
    }

    private OS() {
        super(R.drawable.icon_android_50px, new TriggerProvider() {
        }, OSReflexes.getInstance());
    }
}
