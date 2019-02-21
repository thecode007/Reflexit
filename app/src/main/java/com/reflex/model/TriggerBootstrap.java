package com.reflex.model;

import java.util.ArrayList;

public class TriggerBootstrap {

    private String name;
    private String description;
    private ArrayList<ActionBootstrap> actions;

    public TriggerBootstrap() {

    }

    public TriggerBootstrap(String name, ArrayList<ActionBootstrap> actions, String description) {
        this.name = name;
        this.actions = actions;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
