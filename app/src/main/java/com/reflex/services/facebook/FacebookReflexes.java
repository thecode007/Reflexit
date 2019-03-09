package com.reflex.services.facebook;

import com.reflex.core.providers.ReflexProvider;

import java.util.HashMap;

public class FacebookReflexes extends ReflexProvider {


    protected static FacebookReflexes instance;

    public static ReflexProvider getInstance() {
        if (instance == null) {
            instance = new FacebookReflexes();
        }
        return instance;
    }

    private FacebookReflexes() {
        map = new HashMap<>();

        map.put(DELETE_IMPORTANT_FILE, args -> {
        });
    }
}
