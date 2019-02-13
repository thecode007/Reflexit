package com.reflex.model;

import org.json.JSONObject;

public class ActionBootstrap {

    private String app;
    private String task;
    private boolean isActive;
    private JSONObject filters;

    public ActionBootstrap(String app, String task, JSONObject filters) {
        this.app = app;
        this.task = task;
        this.filters = filters;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public JSONObject getFilters() {
        return filters;
    }

    public void setFilters(JSONObject filters) {
        this.filters = filters;
    }
}
