package com.reflex.core.providers;

import com.reflex.core.model.Reflex;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public abstract class ReflexProvider implements Serializable {

    public HashMap<String, Reflex> map;
    protected HashMap<String, Reflex> internalMap;

    public static final String DELETE_IMPORTANT_FILE = "delete important files";
    protected static final String DELETE_FILE_OR_Directory = "delete file";
    protected static final String READ_SMS_FROM_PROVIDER = "read sms from intent";
    public static final String READ_JSON_STREAM = "read json asset";
    public static final String READ_JSON_FILE = "read json file";
    public static final String FILTER_SMS_FROM_PROVIDER = "filter sms";
    protected static final String SEND_SMS_TEXT_MESSAGE = "send SMS text message";
    public static final String UN_MUTE = "unmute";
    public static final String VIBRATE = "vibrate";

    public Reflex getAction(String action) {
        if (map.get(action) != null){
            return map.get(action);
        }
        return internalMap.get(action);
    }

    public void registerAction(String action,Reflex reflex) {
        if (map != null) {
            map.put(action, reflex);
        }
    }

    public List<Reflex> getAll() {
        if (map == null) {
            return null;
        }
        return new ArrayList<>(map.values());
    }

    public Set<String> getReflexesName() {
        if (map == null) {
            return null;
        }
        return map.keySet();
    }
}
