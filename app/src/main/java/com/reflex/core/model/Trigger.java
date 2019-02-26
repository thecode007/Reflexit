package com.reflex.core.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public abstract class Trigger implements Serializable {

    private String triggerName;
    private String triggerString;
    protected App app;
    protected List<ActionBootstrap> bootstraps;
    private BroadcastReceiver receiver;
    private ObjectMapper mapper;
    protected ArrayList<String> fields;


    public Trigger(String triggerName,String triggerString, App app) {
        this.app = app;
        this.triggerName = triggerName;
        this.triggerString = triggerString;
        mapper= new ObjectMapper();
        bootstraps = Collections.synchronizedList(new ArrayList<>());
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                new Thread(() -> {
                    bindReflexes(context);
                    initReceiverBody(context, intent);
                }).run();
            }
        };
        fields = new ArrayList();

    }





    private void unBindAll() {
        bootstraps.clear();
    }


    @Override
    public boolean equals(Object obj) {
        return obj instanceof Trigger
                && ( triggerString.equals(((Trigger)obj).triggerString) || triggerName.equals(((Trigger)obj).triggerName));
    }

    public String getTriggerString() {
        return triggerString;
    }


    public void register (Context context) {
        Log.wtf(triggerString, "is registered");
        context.registerReceiver(receiver,new IntentFilter(triggerString));
    }

    public void unRegister(Context context) {
        if (receiver != null) {
            context.unregisterReceiver(receiver);
        }
    }

   private  void bindReflexes(Context context) {
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
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

    public List<ActionBootstrap> getBootstraps() {
        return bootstraps;
    }

    public ArrayList<String> getFields() {
        return fields;
    }

    public void setFields(ArrayList<String> fields) {
        this.fields = fields;
    }

    protected  abstract void initReceiverBody(Context context, Intent intent);


    public String getTriggerName() {
        return triggerName;
    }
}
