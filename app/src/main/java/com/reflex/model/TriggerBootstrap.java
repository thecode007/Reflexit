package com.reflex.model;

import java.util.ArrayList;

public class TriggerBootstrap {

    private String name;
    private ArrayList<ActionBootstrap> actions;

    public TriggerBootstrap() {

    }

    public TriggerBootstrap(String name, ArrayList<ActionBootstrap> actions) {
        this.name = name;
        this.actions = actions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ActionBootstrap> getActions() {
        return actions;
    }

    public void setActions(ArrayList<ActionBootstrap> actions) {
        this.actions = actions;
    }

}
