package com.reflex.authentications.login;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginDataService {

    @Headers("Content-Type: application/json")
    @POST("login")
    Call<ResponseBody> loginRequest(@Body JsonObject jsonCredentials);

}
