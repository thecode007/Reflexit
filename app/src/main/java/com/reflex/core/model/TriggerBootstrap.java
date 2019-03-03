package com.reflex.core.model;

import java.util.ArrayList;

public class TriggerBootstrap {

    private String app;
    private ArrayList<ActionBootstrap> actions;

    public TriggerBootstrap() {

    }

    public TriggerBootstrap(String app, ArrayList<ActionBootstrap> actions) {
        this.app = app;
        this.actions = actions;

    }

    public ArrayList<ActionBootstrap> getActions() {
        return actions;
    }

    public void addAction(ActionBootstrap actionBootstrap) {
        actions.add(actionBootstrap);
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public void setActions(ArrayList<ActionBootstrap> actions) {
        this.actions = actions;
    }

}
