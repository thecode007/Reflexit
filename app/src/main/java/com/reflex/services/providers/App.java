package com.reflex.services.providers;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.reflex.services.Reflex;
import com.reflex.services.Trigger;

/**
 * Built for modeling the a trigger and
 * reflex provider. A facade and a commander to the
 * services
 */
public class App {
    // repositories used by the provider
    protected TriggerRepository triggerRepository;
    protected ActionRepository actionRepository;
    protected Context context;

    protected App() {

    }
    public App(@Nullable TriggerRepository triggerRepository, @Nullable ActionRepository actionRepository) {
        this.triggerRepository = triggerRepository;
        this.actionRepository = actionRepository;
    }




    public Reflex getReflex(String name) {
        return actionRepository.getAction(name);
    }

    public void setActionRepository(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    public void execute(String action,Object ...objects) {
        try {
            Reflex reflex = actionRepository.getAction(action);
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
            Reflex reflex = actionRepository.getAction(action);
            if (reflex != null ) {
                reflex.doAction();
            }
        }
        catch (ClassCastException | NullPointerException ex){
            ex.printStackTrace();
        }
    }

    public void register(Trigger trigger) {
        if (triggerRepository != null) {
            triggerRepository.register(trigger);
        }
    }

    public void register(String action,Reflex reflex) {
        if (actionRepository != null) {
            actionRepository.registerAction(action, reflex);
        }
    }

    public void startTriggers(Context context) {
        Log.wtf(this.getClass().getName(), "trigger ");
        if (triggerRepository == null) {

            return;
        }
        for (Trigger trigger: triggerRepository.getAll()) {
            Log.wtf(this.getClass().getName(), "starting trigger:" + trigger.getTriggerString());
            trigger.register(context);
        }
    }

    public void stopTriggers(Context context) {
        if (triggerRepository == null) {
            return;
        }
        for (Trigger trigger: triggerRepository.getAll()) {
            trigger.unRegister(context);
        }
    }
}
