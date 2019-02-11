package com.reflex.services;

import android.content.BroadcastReceiver;

import java.util.ArrayList;

public abstract class Trigger {

    protected String triggerString;
    protected ArrayList<Reflex> boundedActions;
    protected BroadcastReceiver receiver;
    protected ArrayList<ActionConstraints> actionConstraints;


    public Trigger(String triggerString) {
        this.triggerString = triggerString;
    }


}
