package com.reflex.services;

import android.content.Context;
import org.json.JSONObject;
import java.util.HashMap;


public abstract class Trigger {

    protected String triggerString;
    protected HashMap<String,Reflex> reflexHashMap;
    protected JSONObject filterFields;
    protected Context context;

    public Trigger(Context context, String triggerString) {
        this.triggerString = triggerString;
        this.context = context;
        reflexHashMap = new HashMap<>();
        filterFields = new JSONObject();
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

    private void unBindAll() {
        reflexHashMap.clear();
    }


    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof Trigger
                && triggerString.equals(((Trigger)obj).triggerString);
    }
}
