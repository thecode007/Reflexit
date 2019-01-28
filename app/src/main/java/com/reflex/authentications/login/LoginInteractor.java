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

    public Retrofit getRetrofitInstance() {
        return retrofitInstance;
    }

    public void setRetrofitInstance(Retrofit retrofitInstance) {
        this.retrofitInstance = retrofitInstance;
    }

    /**
     *  The contract that needs to be assigned when the
     *  login is done which are validations to the fields
     **/
    interface OnLoginDoneListener {

        void onInvalidEmail();

        void onInvalidPassword();

        void onSuccess(JSONObject jsonResult)  throws JSONException ;

        void onFail();
    }

     /**
     *  The login function which process the credentials
     **/
     void login(final String email, final String password,
                final OnLoginDoneListener listener) {

             String regex = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
             Pattern pattern = Pattern.compile(regex);
             if (TextUtils.isEmpty(email) || !pattern.matcher(email).matches()) {
                 listener.onInvalidEmail();
                 return;
             }
             if (TextUtils.isEmpty(password)) {
                 listener.onInvalidPassword();
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
    }
