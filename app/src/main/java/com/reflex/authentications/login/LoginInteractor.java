package com.reflex.authentications.login;

import android.os.Handler;
import android.text.TextUtils;

/**
 *  Built for fetching login credentials data from the server
 **/

class LoginInteractor {

    /**
     *  The contract the need be assigned when the login is done which are validations to the fields
     **/
    interface OnLoginDoneListener {

        void onInvalidUsername();

        void onInvalidPassword();

        void onSuccess();
    }

     /**
     *  The login function which process the credentials
     **/
     void login(final String username, final String password, final OnLoginDoneListener listener) {
         // Mock login. I'm creating a handler to delay the answer a couple of seconds
         new Handler().postDelayed(() -> {
             if (TextUtils.isEmpty(username)) {
                 listener.onInvalidUsername();
                 return;
             }
             if (TextUtils.isEmpty(password)) {
                 listener.onInvalidPassword();
                 return;
             }
             listener.onSuccess();
            }, 2000);
        }
    }
