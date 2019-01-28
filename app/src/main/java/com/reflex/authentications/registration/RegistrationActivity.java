package com.reflex.authentications.registration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.reflex.R;

public class RegistrationActivity extends AppCompatActivity implements RegisterationView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setEmailError() {

    }

    @Override
    public void setPasswordError() {

    }

    @Override
    public void setUserNameError() {

    }

    @Override
    public void setPasswordConfirmError() {

    }

    @Override
    public void setFormError() {

    }

    @Override
    public void setSharedPreference(String key, String jsonString) {

    }

    @Override
    public void navigateToHome() {

    }
}
