package com.reflex.services.providers;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Built for modeling the a trigger and
 * reflex provider. A facade and a commander to the
 * services
 */
public class App {
    // repositories used by the provider
    protected TriggerProvider triggerProvider;
    protected ReflexProvider reflexProvider;
    protected Context context;

    protected App() {

    }
    public App(@Nullable TriggerProvider triggerProvider, @Nullable ReflexProvider reflexProvider) {
        this.triggerProvider = triggerProvider;
        this.reflexProvider = reflexProvider;
    }




    public Reflex getReflex(String name) {
        return reflexProvider.getAction(name);
    }

    public void setReflexProvider(ReflexProvider reflexProvider) {
        this.reflexProvider = reflexProvider;
    }

    public void execute(String action,Object ...objects) {
        try {
            Reflex reflex = reflexProvider.getAction(action);
            if (reflex != null ) {
                reflex.doAction(objects);
            }
        }
        catch (ClassCastException | NullPointerException ex){
            ex.printStackTrace();
        }

    }

    public void execute(String action) {
        try {
            Reflex reflex = reflexProvider.getAction(action);
            if (reflex != null ) {
                reflex.doAction();
            }
        }
        catch (ClassCastException | NullPointerException ex){
            ex.printStackTrace();
        }
    }

    public void register(Trigger trigger) {
        if (triggerProvider != null) {
            triggerProvider.register(trigger);
        }
    }

    public void register(String action,Reflex reflex) {
        if (reflexProvider != null) {
            reflexProvider.registerAction(action, reflex);
        }
    }

    public void startTriggers(Context context) {
        Log.wtf(this.getClass().getName(), "trigger ");
        if (triggerProvider == null) {

            return;
        }
        for (Trigger trigger: triggerProvider.getAll()) {
            Log.wtf(this.getClass().getName(), "starting trigger:" + trigger.getTriggerString());
            trigger.register(context);
        }
    }

    public void stopTriggers(Context context) {
        if (triggerProvider == null) {
            return;
        }
        for (Trigger trigger: triggerProvider.getAll()) {
            trigger.unRegister(context);
        }
    }
}
