package com.reflex.model;

import java.util.ArrayList;

public class TriggerBootstrap {

    private String name;
    private ArrayList<ActionBootstrap> bootstraps;

    public TriggerBootstrap(String name, ArrayList<ActionBootstrap> bootstraps) {
        this.name = name;
        this.bootstraps = bootstraps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ActionBootstrap> getBootstraps() {
        return bootstraps;
    }

    public void setBootstraps(ArrayList<ActionBootstrap> bootstraps) {
        this.bootstraps = bootstraps;
    }
}
