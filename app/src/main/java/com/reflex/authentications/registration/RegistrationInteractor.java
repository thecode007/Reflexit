package com.reflex.authentications.registration;

import android.util.Log;
import com.google.gson.JsonObject;
import com.reflex.network.RetrofitSingleton;
import com.reflex.util.ResponseCallBack;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.internal.EverythingIsNonNull;


/**
 * Built for interaction between the api/database and the presenter that controls the ui
*/
class RegistrationInteractor {

    private static Retrofit retrofit = RetrofitSingleton.getInstance();
    private static RegistrationDataService dataService = retrofit.create(RegistrationDataService.class);

    /**
     *  built for requesting email uniqueness check from the api
     *  @param  responseCallBack represents the ui logic upon each network response which is implemented by the presenter
     */
    static void checkEmail(String email, ResponseCallBack responseCallBack) {

        JsonObject emailJson = new JsonObject();
        emailJson.addProperty("email",email);
        dataService.checkEmailAvailabilityRequest(emailJson)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    @EverythingIsNonNull
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.body()!= null) {
                            try {
                                JSONObject responseJson = new JSONObject(response.body().string());
                                String code = responseJson.getString("code");
                                if ( code != null && code.equals("404")) {
                                    String error = responseJson.getString("error");
                                    Log.wtf("s",error);
                                    responseCallBack.onFail(error);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    @EverythingIsNonNull
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

        try {
            responseCallBack.onSuccess(null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     *  built for requesting name uniqueness check from the api
     *  @param  responseCallBack represents the ui logic upon each network response which is implemented by the presenter
     */
    static void checkName(String name, ResponseCallBack responseCallBack) {
        JsonObject nameJson = new JsonObject();
        nameJson.addProperty("name", name);
        dataService.checkNameAvailabilityRequest(nameJson).enqueue(new Callback<ResponseBody>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body()!= null) {
                    try {
                        JSONObject responseJson = new JSONObject(response.body().string());
                        String code = responseJson.getString("code");
                        if ( code != null && code.equals("404")) {
                            String error = responseJson.getString("error");
                            responseCallBack.onFail(error);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            @EverythingIsNonNull
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
        try {
            responseCallBack.onSuccess(null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     *  built for requesting registration from the api
     *  @param  responseCallBack represents the ui logic upon each network response which is implemented by the presenter
     */
    static void sendRegisterRequest(String email, String name,
                   String password, String passwordConfirm, ResponseCallBack responseCallBack) {
        JsonObject registerReqObject = new JsonObject();
        registerReqObject.addProperty("email", email);
        registerReqObject.addProperty("name", name);
        registerReqObject.addProperty("password", password);
        registerReqObject.addProperty("password_confirmation", passwordConfirm);

        dataService.registerRequest(registerReqObject).enqueue(new Callback<ResponseBody>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    try {
                        JSONObject responseJson = new JSONObject(response.body().string());
                        String code = responseJson.getString("code");
                        if ( code == null || code.equals("404")) {
                            responseCallBack.onFail(responseJson.getString("error"));
                            return;
                        }
                        responseCallBack.onSuccess(
                                responseJson
                                .getJSONObject("data")
                        );
                    } catch (JSONException e) {
                        responseCallBack.onFail("Parse JSONException");
                        e.printStackTrace();
                    } catch (IOException e) {
                        responseCallBack.onFail("IOException");
                        e.printStackTrace();
                    }
                }
            }
            @Override
            @EverythingIsNonNull
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                responseCallBack.onFail("No Network Response");
            }
        });
    }
}
