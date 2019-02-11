package com.reflex.services.sms;

import com.reflex.services.Reflex;

import java.util.ArrayList;
import java.util.HashMap;

public class SmsReflexesPool {

    private static SmsReflexesPool instance;
    private  HashMap<String, ArrayList<Reflex>> reflexes;

    private SmsReflexesPool() {
        reflexes = new HashMap<>();
    }

    public static SmsReflexesPool getInstance() {
        if (instance == null) {
            instance = new SmsReflexesPool();
        }
        return instance;
    }

    public  void add(String trigger, Reflex reflex) {
        ArrayList<Reflex> reflexesList = reflexes.get(trigger);
        if (reflexesList == null) {
            reflexesList = new ArrayList<>();
            reflexes.put(trigger, reflexesList);
        }
        reflexesList.add(reflex);
    }

    public  ArrayList<Reflex> getReflexes(String trigger) {
        ArrayList<Reflex> reflexesList = reflexes.get(trigger);
        if (reflexesList == null) {
            reflexesList = new ArrayList<>();
        }
        return reflexesList;
    }

}
