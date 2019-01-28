package com.reflex.authentications.registration;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RegistrationService {

    @Headers("Content-Type: application/json")
    @POST("register")
    Call<ResponseBody> registerRequest(@Body JsonObject jsonCredentials);

}
