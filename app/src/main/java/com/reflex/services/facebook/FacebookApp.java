package com.reflex.services.facebook;

import android.app.Activity;

import com.facebook.AccessToken;
import com.reflex.R;
import com.reflex.core.model.App;
import com.reflex.core.model.WebBased;
import com.reflex.core.providers.ReflexProvider;

public class FacebookApp extends App implements WebBased {

    public static FacebookApp instance;
    public static FacebookApp getInstance() {
        if (instance == null) {
            instance = new FacebookApp();
        }
        return instance;
    }


    private FacebookApp() {
        super(R.drawable.icon_facebook_50px, null,
               null);
    }

    @Override
    public void execute(String action) {
        if (isLoggedIn()) {
            super.execute(action);
        }
    }


    @Override
    public void execute(String action, Object... objects) {
        if (isLoggedIn()) {
            super.execute(action, objects);
        }
    }


    private boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null && !accessToken.isExpired();
    }

    @Override
    public Activity getActivity() {
        return new FacebookLoginActivity();
    }
}
