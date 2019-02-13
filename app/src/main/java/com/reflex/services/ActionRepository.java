package com.reflex.services;

import android.util.Log;
import java.util.HashMap;

public abstract class ActionRepository {

    protected HashMap<String, Reflex> map;
    private static String TAG = ActionRepository.class.getName();

    public static final String DELETE_IMPORTANT_FILE = "delete_important_files";
    public static final String DELETE_FILE_OR_Directory = "delete_file";
    public static final String READ_SMS_FROM_PROVIDER = "read_sms_from_intent";
    public static final String READ_JSON_STREAM = "read_json_asset";

    public Reflex getAction(String action) {
        return map.get(action);
    }

    public void execute(String action,Object ...objects) {
        try {
            if (map.get(action) != null ) {
                getAction(action).doAction(objects);
            }
        }
        catch (ClassCastException | NullPointerException ex){
            ex.printStackTrace();
        }

    }

    public void execute(String action) {
        try {
            if (map.get(action) != null ) {
                getAction(action).doAction();
            }
        }
        catch (ClassCastException | NullPointerException ex){
            ex.printStackTrace();
        }
    }
}
