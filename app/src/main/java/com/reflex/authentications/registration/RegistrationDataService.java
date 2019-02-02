package com.reflex.authentications.registration;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RegistrationDataService {

    @Headers("Content-Type: application/json")
    @POST("register")
    Call<ResponseBody> registerRequest(@Body JsonObject jsonCredentials);

    @Headers("Content-Type: application/json")
    @POST("checkName")
    Call<ResponseBody> checkNameAvailabilityRequest(@Body JsonObject jsonCredentials);

    @Headers("Content-Type: application/json")
    @POST("checkEmail")
    Call<ResponseBody> checkEmailAvailabilityRequest(@Body JsonObject jsonCredentials);

}
