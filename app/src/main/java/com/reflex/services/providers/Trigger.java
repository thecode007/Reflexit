package com.reflex.services.providers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reflex.model.ActionBootstrap;
import com.reflex.model.TriggerBootstrap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public abstract class Trigger {

    private String triggerString;
    protected App app;
    private HashMap<String, Reflex> reflexHashMap;
    protected List<ActionBootstrap> bootstraps;
    private BroadcastReceiver receiver;
    private ObjectMapper mapper;


    public Trigger(String triggerString, App app) {
        this.triggerString = triggerString;
        mapper= new ObjectMapper();
        reflexHashMap = new HashMap<>();
        bootstraps = new ArrayList<>();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                bindReflexes(context);
                initReceiverBody(context, intent);
            }
        };
        this.app = app;
    }


    private void bindReflex(String actionProvider, String action) {
        App provider = AppProvider.getInstance()
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
        return obj instanceof Trigger
                && triggerString.equals(((Trigger)obj).triggerString);
    }

    public String getTriggerString() {
        return triggerString;
    }


    public void register (Context context) {
        context.registerReceiver(receiver,new IntentFilter(triggerString));
    }

    public void unRegister(Context context) {
        if (receiver != null) {
            context.unregisterReceiver(receiver);
        }
    }

   private void bindReflexes(Context context) {
       try {
           unBindAll();
           JsonNode node = mapper.readTree(context.getAssets().open("bootstrap-trigger.json"));
           JsonNode bootstrap = node.get(triggerString);

           if (bootstrap == null) {
               return;
           }
           TriggerBootstrap trigger = mapper.readValue(bootstrap.toString(), TriggerBootstrap.class);
           bootstraps.addAll(trigger.getActions());
           if (bootstraps == null || bootstraps.size() == 0) {
               return;
           }

           for (ActionBootstrap action : bootstraps) {
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


}
