package com.reflex.services.providers;

import android.support.annotation.Nullable;
import com.reflex.services.Reflex;
import com.reflex.services.Trigger;

/**
 * Built for modeling the a trigger and
 * reflex provider. A facade and a commander to the
 * services
 */
public class App {

    public static String SMS = "sms";
    public static String FILE_SYSTEM = "file_system";
    // repositories used by the provider
    private TriggerRepository triggerRepository;
    private ActionRepository actionRepository;

    public App(@Nullable TriggerRepository triggerRepository, @Nullable ActionRepository actionRepository) {
        this.triggerRepository = triggerRepository;
        this.actionRepository = actionRepository;
    }

    public TriggerRepository getTriggerRepository() {
        return triggerRepository;
    }

    public void setTriggerRepository(TriggerRepository triggerRepository) {
        this.triggerRepository = triggerRepository;
    }

    public ActionRepository getActionRepository() {
        return actionRepository;
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

    public void startTriggers() {
        if (triggerRepository == null) {
            return;
        }
        for (Trigger trigger: triggerRepository.getAll()) {
            trigger.register();
        }
    }

    public void stopTriggers() {
        for (Trigger trigger: triggerRepository.getAll()) {
            trigger.unRegister();
        }
    }
}
