package com.reflex.core.providers;

import com.reflex.core.model.Reflex;

import java.io.Serializable;
import java.util.HashMap;

public abstract class ReflexProvider implements Serializable {

    public HashMap<String, Reflex> map;

    public static final String DELETE_IMPORTANT_FILE = "delete important files";
    public static final String DELETE_FILE_OR_Directory = "delete file";
    public static final String READ_SMS_FROM_PROVIDER = "read_sms_from_intent";
    public static final String READ_JSON_STREAM = "read_json_asset";
    public static final String FILTER_SMS_FROM_PROVIDER = "filter sms";
    public static final String SEND_SMS_TEXT_MESSAGE = "send SMS text message";

    public Reflex getAction(String action) {
        return map.get(action);
    }


    public void registerAction(String action,Reflex reflex) {
        if (map != null) {
            map.put(action, reflex);
        }
    }
}
