package com.reflex.core.model;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.reflex.core.providers.ReflexProvider;
import com.reflex.core.providers.TriggerProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Built for modeling the a trigger and
 * reflex provider. A facade and a commander to the
 * services
 */
public class App implements Serializable {
    // repositories used by the provider
    protected Context context;
    protected TriggerProvider triggerProvider;
    protected ReflexProvider reflexProvider;
    protected int iconResource;
    protected App() {

    }
    public App(int iconResource, @Nullable TriggerProvider triggerProvider, @Nullable ReflexProvider reflexProvider) {
        this.triggerProvider = triggerProvider;
        this.reflexProvider = reflexProvider;
        this.iconResource = iconResource;
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

    public int getIconResource() {
        return iconResource;
    }

    public Set<String> showActions() {
        if (reflexProvider != null && reflexProvider.map != null) {
            return reflexProvider.map.keySet();
        }
        return null;
    }

    public Trigger getTrigger(String name) {
        Collection<Trigger> triggers = triggerProvider.getAll();
        if (triggers == null ) {
            return null;
        }
        for(Trigger trigger : triggers) {
            if (trigger.getTriggerName().equals(name)) {
                return trigger;
                }
            }
        return null;
    }


    public List<Trigger> getTriggers() {
        if (triggerProvider == null) {
            return null;
        }
        return new ArrayList(triggerProvider.getAll());
    }

    public List<Reflex> getReflexes() {
        if (reflexProvider == null) {
            return null;
        }
        return new ArrayList(reflexProvider.getAll());
    }

}
