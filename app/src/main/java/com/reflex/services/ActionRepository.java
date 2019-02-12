package com.reflex.services;

import java.util.HashMap;

public class ActionRepository {

    protected HashMap<String, Reflex> map;
    protected static ActionRepository instance;

    public static final String DELETE_IMPORTANT_FILE = "delete_important_files";
    public static final String DELETE_FILEOrDirectory = "delete_file";
    public static final String READ_JSON_STREAM = "read_json_asset";

    public Reflex getAction(String action) {
        return map.get(action);
    }

    public void execute(String action,Object ...objects) {
        try {
            if (map.get(action) !=null ) {
                getAction(action).doAction(objects);
            }

        }
        catch (ClassCastException | NullPointerException ex){
            ex.printStackTrace();
        }

    }

    public void execute(String action) {
        try {
            if (map.get(action) !=null ) {
                getAction(action).doAction();
            }
        }
        catch (ClassCastException | NullPointerException ex){
            ex.printStackTrace();
        }
    }
}
