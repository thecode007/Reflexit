package com.reflex.services;

import android.content.Context;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public abstract class Trigger {

    protected String triggerString;
    protected HashMap<String,Reflex> reflexHashMap;
    protected Context context;


    public Trigger(Context context, String triggerString) {
        this.triggerString = triggerString;
        this.context = context;
        reflexHashMap = new HashMap<>();
    }


    public void bindReflex(String actionProvider,String action) {
        ActionRepository provider = ActionProvider.getInstance()
                .getActionProvider(actionProvider);
        Reflex reflex;
        if (provider != null &&  (reflex = provider.getAction(action)) !=null) {
            reflexHashMap.put(action,reflex);
        }
    }

    public void unBindReflex(String reflex) {
        if (reflexHashMap.get(reflex) != null) {
            reflexHashMap.remove(reflex);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof Trigger
                && triggerString.equals(((Trigger)obj).triggerString);
    }
}
