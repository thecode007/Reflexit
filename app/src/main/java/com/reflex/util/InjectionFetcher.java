package com.reflex.util;

import android.content.Context;

import com.reflex.services.fileSystem.FileSystemActions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class InjectionFetcher {

    public static JSONObject getInjection(Context context, String service, String reflex) throws IOException, JSONException {

        return FileSystemActions.readJSONFromAsset(context.getAssets()
                .open("app.json"))
                .getJSONObject(service)
                .getJSONObject(reflex);
    }
}
