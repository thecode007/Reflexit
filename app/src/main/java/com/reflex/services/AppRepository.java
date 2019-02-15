package com.reflex.services;

import com.reflex.services.fileSystem.FileSystem;
import com.reflex.services.providers.App;
import com.reflex.services.sms.SmsApp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppRepository {

    private HashMap<String, App> repoHashMap;
    private static AppRepository actionProvider;
    public static String SMS = "sms";
    public static String FILE_SYSTEM = "file_system";


    public static AppRepository getInstance() {
        if (actionProvider == null) {
            actionProvider = new AppRepository();
        }
        return actionProvider;
    }

    private AppRepository() {
        repoHashMap = new HashMap<>();
        repoHashMap.put(FILE_SYSTEM, SmsApp.getInstance());
        repoHashMap.put(SMS, FileSystem.getInstance());
    }

    public App getApp(String provider) {
        return repoHashMap.get(provider);
    }

    public List<App> getAllApps() {
        return new ArrayList<>(repoHashMap.values());
    }
}
