package com.reflex.services;

import com.reflex.services.fileSystem.FileSystem;
import com.reflex.services.fileSystem.FileSystemActions;
import com.reflex.services.providers.ActionRepository;
import com.reflex.services.providers.App;
import com.reflex.services.sms.SmsApp;

import java.util.HashMap;

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


    public App getActionProvider(String provider) {
        return repoHashMap.get(provider);
    }
}
