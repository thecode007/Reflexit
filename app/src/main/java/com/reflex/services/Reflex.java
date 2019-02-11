package com.reflex.services;

import android.content.Context;
import android.content.Intent;

import org.json.JSONException;

import java.io.IOException;

public interface Reflex {

    void doAction(Context context, Intent intent) throws IOException, JSONException;
}


