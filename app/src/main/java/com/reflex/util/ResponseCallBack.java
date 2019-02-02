package com.reflex.util;


import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

public interface ResponseCallBack {

    void onSuccess(@Nullable JSONObject responseData) throws JSONException;

    void onFail(String errorMessage);
}
