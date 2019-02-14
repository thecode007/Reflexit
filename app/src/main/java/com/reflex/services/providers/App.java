package com.reflex.services.providers;

import android.support.annotation.Nullable;
import com.reflex.services.Reflex;
import com.reflex.services.Trigger;

public class App {

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
        }
    }

    public void register(String action,Reflex reflex) {
        if (actionRepository != null) {
            actionRepository.registerAction(action, reflex);
        }
    }

    public void startTriggers() {
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
