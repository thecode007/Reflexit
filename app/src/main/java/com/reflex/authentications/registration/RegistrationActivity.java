package com.reflex.authentications.registration;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.reflex.HomeActivity;
import com.reflex.R;
import com.reflex.databinding.ActivityRegistrationBinding;

public class RegistrationActivity extends AppCompatActivity implements RegistrationView{

    private RegistrationViewModel registrationViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegistrationBinding registrationBinding =  DataBindingUtil.setContentView(this, R.layout.activity_registration);
        registrationViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);
        registrationBinding.setViewmodel(registrationViewModel);
        registrationBinding.setLifecycleOwner(this);
        RegistrationPresenter presenter = new RegistrationPresenter(registrationViewModel, this);
        findViewById(R.id.btn_register).setOnClickListener(view -> presenter.register());
    }

    @Override
    public void navigateToHome() {
        startActivity(new Intent(this, HomeActivity.class));
        getParent().finish();
        finish();
    }

    @Override
    public void saveToPrefs(String key, String jsonString) {
        Context ctx = getApplicationContext();
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("user"
                , MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, jsonString);
        editor.apply();
    }
}
