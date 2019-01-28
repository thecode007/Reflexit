package com.reflex.authentications.login;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonObject;
import com.reflex.network.NetworkConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.internal.EverythingIsNonNull;

/**
 *  Built for fetching login credentials data from the server
 **/

class LoginInteractor {

    private Retrofit retrofitInstance;
    private LoginDataService loginDataService;


    LoginInteractor(Retrofit retrofitInstance) {

        this.retrofitInstance = retrofitInstance;

        loginDataService = retrofitInstance.create(LoginDataService.class);
    }


    /**
     *  The contract that needs to be assigned when the
     *  login is done when handling response
     **/
    interface OnLoginDoneListener {

        void onSuccess(JSONObject jsonResult)  throws JSONException ;

        void onFail();
    }

    /**
     *  The contract that needs to be assigned when the
     *  user is typing and it will be used for validation
     *  before the login is done
     **/
    interface  OnLoginFieldChangeListener {

        void onValidEmail();

        void onValidPassword();

        void onInvalidEmail();

        void onInvalidPassword();
    }



     /**
     *  The login function which process the credentials
     **/
     void login(final String email, final String password,
                final OnLoginDoneListener listener, final OnLoginFieldChangeListener changeListener) {

             if (!validateFields(email, password,
                 changeListener)) {
                 return;
              }
             JsonObject jsonObject = new JsonObject();
             jsonObject.addProperty("email",email);
             jsonObject.addProperty("password",password);

             loginDataService.loginRequest(jsonObject).enqueue(new Callback<ResponseBody>() {
                 @Override
                 @EverythingIsNonNull
                 public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                     try {
                         if (response.body() == null) {
                             listener.onFail();
                             return;
                         }
                         JSONObject json = new JSONObject(response.body().string());
                         if (json.getString("code").equals("404") || json.getString("code").equals("500")) {
                             listener.onFail();
                             return;
                         }
                         listener.onSuccess(json);
                     } catch (IOException e) {
                         listener.onFail();
                         Log.wtf("Login Form Error", e.getMessage());
                         e.printStackTrace();
                     } catch (JSONException e) {
                         listener.onFail();
                         e.printStackTrace();
                         Log.wtf("Login Form Error", e.getMessage());
                     }
                 }

                 @Override
                 @EverythingIsNonNull
                 public void onFailure(Call<ResponseBody> call, Throwable t) {
                     listener.onFail();
                     Log.wtf("Login Form Error", t.getMessage());
                     t.printStackTrace();
                 }
             });
        }


     boolean validateFields(final String email, final String password,
                          final  OnLoginFieldChangeListener changeListener) {

         String regex = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
         Pattern pattern = Pattern.compile(regex);
         if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && pattern.matcher(email).matches()) {
             changeListener.onValidEmail();
             changeListener.onValidPassword();
             return true;
         }

         if (TextUtils.isEmpty(email) || !pattern.matcher(email).matches()) {
             changeListener.onInvalidEmail();
         }
         else {
             changeListener.onValidEmail();
         }

         if (TextUtils.isEmpty(password)) {
             changeListener.onInvalidPassword();
         }
         else {
             changeListener.onValidPassword();
         }
         return false;
     }
    }
