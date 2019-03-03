package com.reflex.services;

import android.system.Os;

import com.reflex.core.model.App;
import com.reflex.services.fileSystem.FileSystem;
import com.reflex.services.os.OS;
import com.reflex.services.sms.SmsApp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppProvider {

    private HashMap<String, App> repoHashMap;
    private static AppProvider actionProvider;
    public static String SMS = SmsApp.class.getSimpleName();
    public static String FILE_SYSTEM = FileSystem.class.getSimpleName();
    public static String OS = com.reflex.services.os.OS.class.getSimpleName();


    public static AppProvider getInstance() {
        if (actionProvider == null) {
            actionProvider = new AppProvider();
        }
        return actionProvider;
    }

    private AppProvider() {
        repoHashMap = new HashMap<>();
        repoHashMap.put(SMS, SmsApp.getInstance());
        repoHashMap.put(FILE_SYSTEM, FileSystem.getInstance());
        repoHashMap.put(OS, com.reflex.services.os.OS.getInstance());
    }

    public App getApp(String provider) {
        return repoHashMap.get(provider);
    }

    public List<App> getAllApps() {
        return new ArrayList<>(repoHashMap.values());
    }

    public  List<App> getTriggerProviders() {
        ArrayList<App> apps = new ArrayList<>(repoHashMap.values());
        ArrayList<App> result = new ArrayList<>();
        for (App app : apps) {
            if (app.getTriggers() == null || app.getTriggers().size() == 0){
                continue;
            }
            result.add(app);
        }
        return result;
    }

    public  List<App> getReflexProviders() {
        ArrayList<App> apps = new ArrayList<>(repoHashMap.values());
        ArrayList<App> result = new ArrayList<>();
        for (App app : apps) {
            if (app.getReflexes() == null || app.getReflexes().size() == 0){
                continue;
            }
            result.add(app);
        }
        return result;
    }
}
