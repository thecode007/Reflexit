package com.reflex.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reflex.model.ActionBootstrap;
import com.reflex.model.TriggerBootstrap;
import com.reflex.services.providers.App;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public abstract class Trigger {

    protected String triggerString;
    protected App app;
    protected HashMap<String, Reflex> reflexHashMap;
    protected JsonNode filterFields;
    protected List<ActionBootstrap> bootstraps;
    protected Context context;
    protected BroadcastReceiver receiver;
    protected ObjectMapper mapper;


    public Trigger(Context context, String triggerString, App app) {
        this.triggerString = triggerString;
        this.context = context;
        mapper= new ObjectMapper();
        reflexHashMap = new HashMap<>();
        filterFields = mapper.createObjectNode();
        bootstraps = new ArrayList<>();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                bindReflexes();
                initReceiverBody(context, intent);
            }
        };
        registerToApp(app);
    }


    public void bindReflex(String actionProvider,String action) {
        App provider = AppRepository.getInstance()
                .getApp(actionProvider);
        Reflex reflex;
        if (provider != null &&  (reflex = provider.getReflex(action)) != null) {
            reflexHashMap.put(action,reflex);
        }
    }

    public void unBindReflex(String reflex) {
        if (reflexHashMap.get(reflex) != null) {
            reflexHashMap.remove(reflex);
        }
    }

    private void unBindAll() {
        bootstraps.clear();
        reflexHashMap.clear();
    }


    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof Trigger
                && triggerString.equals(((Trigger)obj).triggerString);
    }

    public String getTriggerString() {
        return triggerString;
    }

    public void setTriggerString(String triggerString) {
        this.triggerString = triggerString;
    }

    public void register () {
        context.registerReceiver(receiver,new IntentFilter(triggerString));
    }

    public void unRegister() {
        if (receiver != null) {
            context.unregisterReceiver(receiver);
        }
    }

   protected void bindReflexes() {
       try {
           unBindAll();
           JsonNode node = mapper.readTree(context.getAssets().open("bootstrap-trigger.json"));
           JsonNode bootstrap = node.get(triggerString);
           if (bootstrap == null) {
               return;
           }
           TriggerBootstrap trigger = mapper.readValue(bootstrap.toString(), TriggerBootstrap.class);
           List<ActionBootstrap> wraps = trigger.getActions();

           if (bootstraps == null || bootstraps.size() == 0) {
               return;
           }

           for (ActionBootstrap action : wraps) {
               if (!action.getActive())
                   continue;
               bindReflex(action.getApp(), action.getAction());
               bootstraps.add(action);
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
   }


    protected  abstract void initReceiverBody(Context context, Intent intent);
    protected  void registerToApp(App app) {
        app.register(this);
    }

}
