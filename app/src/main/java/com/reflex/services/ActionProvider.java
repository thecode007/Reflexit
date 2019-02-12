package com.reflex.services;

import com.reflex.services.fileSystem.FileSystemActions;

import java.util.HashMap;

public class ActionProvider {

    private HashMap<String, ActionRepository> repoHashMap;
    private static ActionProvider actionProvider;

    public static String SMS = "sms";
    public static String FILE_SYSTEM = "file_system";


    public static ActionProvider getInstance() {
        if (actionProvider == null) {
            actionProvider = new ActionProvider();
        }
        return actionProvider;
    }

    private ActionProvider() {
        repoHashMap = new HashMap<>();
        repoHashMap.put(FILE_SYSTEM, FileSystemActions.getInstance());
        repoHashMap.put(SMS, FileSystemActions.getInstance());
    }


    public ActionRepository getActionProvider(String provider) {
        return repoHashMap.get(provider);
    }


}
