package com.reflex.model;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.json.JSONObject;

public class ActionBootstrap {

    private String app;
    private String action;
    private boolean active;
    private ObjectNode constraints;

    public ActionBootstrap() {

    }
    public ActionBootstrap(String app, String action, ObjectNode constraints) {
        this.app = app;
        this.action = action;
        this.constraints = constraints;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @JsonRawValue
    public ObjectNode getConstraints() {
        return constraints;
    }

    public void setConstraints(ObjectNode constraints) {
        this.constraints = constraints;
    }
}
