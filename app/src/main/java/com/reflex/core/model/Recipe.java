package com.reflex.core.model;

public class Recipe {

    public String appName;
    public int appImageResource;
    public String triggerName;
    public String targetAppName;
    public int targetAppImageResource;
    public String description;
    public boolean isActive;

    public Recipe(String appName, int appImageResource, String triggerName,
                  String targetAppName, int targetAppImageResource, String description,
                  boolean isActive) {
        this.appName = appName;
        this.appImageResource = appImageResource;
        this.triggerName = triggerName;
        this.targetAppName = targetAppName;
        this.targetAppImageResource = targetAppImageResource;
        this.description = description;
        this.isActive = isActive;
    }
}
