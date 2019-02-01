package com.reflex.authentications.registration;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonObject;
import com.reflex.network.RetrofitSingleton;
import com.reflex.util.Clickable;

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

public class RegistrationPresenter {


    RegistrationPresenter(RegistrationViewModel viewModel, RegistrationActivity registrationActivity) {
        String regex = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Retrofit retrofit = RetrofitSingleton.getInstance();
        RegistrationDataService dataService = retrofit.create(RegistrationDataService.class);

        viewModel.email.observe(registrationActivity, s -> {
            if (TextUtils.isEmpty(viewModel.email.getValue())) {
                viewModel.emailError.setValue("This Field is required");
                viewModel.isValidEmail.setValue(false);
                checkFormValidity(viewModel);
                return;
            }

            if (!pattern.matcher(viewModel.email.getValue()).matches()) {
                viewModel.emailError.setValue("Invalid Email Format");
                viewModel.isValidEmail.setValue(false);
                checkFormValidity(viewModel);
                return;
            }

            JsonObject emailJson = new JsonObject();
            emailJson.addProperty("email",s);

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
                                viewModel.emailError.setValue(error);
                                viewModel.isValidEmail.setValue(false);
                                checkFormValidity(viewModel);
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    viewModel.isValidEmail.setValue(true);
                }

                @Override
                @EverythingIsNonNull
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });

            viewModel.isValidEmail.setValue(true);
            checkFormValidity(viewModel);
        });



        viewModel.name.observe(registrationActivity, s -> {
            if (TextUtils.isEmpty(viewModel.name.getValue())) {
                viewModel.nameError.setValue("This Field is required");
                viewModel.isValidName.setValue(false);
                checkFormValidity(viewModel);
                return;
            }
            JsonObject nameJson = new JsonObject();
            nameJson.addProperty("name", s);
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
                                viewModel.nameError.setValue(error);
                                viewModel.isValidName.setValue(false);
                                checkFormValidity(viewModel);
                                return;
                            }
                            checkFormValidity(viewModel);
                        } catch (JSONException e) {
                            checkFormValidity(viewModel);
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

            viewModel.isValidName.setValue(true);
            checkFormValidity(viewModel);
        });

        viewModel.password.observe(registrationActivity, s -> {

            if (TextUtils.isEmpty(viewModel.password.getValue())) {
                viewModel.passwordError.setValue("This Field is required");
                viewModel.isValidPassword.setValue(false);
                checkFormValidity(viewModel);
                return;
            }

            if (!TextUtils.isEmpty(viewModel.passwordConfirm.getValue()) &&
                    !viewModel.passwordConfirm.getValue()
                    .equals(viewModel.password.getValue())){

                viewModel.passwordConfirmError.setValue("Password mismatch");
                viewModel.isValidPasswordConfirm.setValue(false);
                checkFormValidity(viewModel);
                return;
            }
            viewModel.isValidPassword.setValue(true);
            viewModel.isValidPasswordConfirm.setValue(true);
            checkFormValidity(viewModel);
        });

        viewModel.passwordConfirm.observe(registrationActivity, s -> {

            if (!TextUtils.isEmpty(viewModel.passwordConfirm.getValue()) &&
                    !viewModel.passwordConfirm.getValue()
                            .equals(viewModel.password.getValue())){

                viewModel.passwordConfirmError.setValue("Password mismatch");
                viewModel.isValidPasswordConfirm.setValue(false);
                checkFormValidity(viewModel);
                return;
            }
            viewModel.isValidPassword.setValue(true);
            viewModel.isValidPasswordConfirm.setValue(true);
            checkFormValidity(viewModel);
        });
    }


    void checkFormValidity(RegistrationViewModel viewModel) {

        if (viewModel.isValidPassword.getValue() == null ||
                viewModel.isValidPasswordConfirm.getValue() == null ||
                viewModel.isValidName.getValue() == null ||
                viewModel.isValidEmail.getValue() == null) {
            return;
        }

        viewModel.isValidForm.setValue(viewModel.isValidPassword.getValue() &&
                viewModel.isValidPasswordConfirm.getValue() &&
                viewModel.isValidName.getValue() &&
                viewModel.isValidEmail.getValue());
    }


    public void navigateHome() {

    }



}
