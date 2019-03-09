package com.reflex.authentications.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.reflex.ui.HomeActivity;
import com.reflex.R;
import com.reflex.authentications.registration.RegistrationActivity;
import com.reflex.network.RetrofitSingleton;


public class LoginActivity extends AppCompatActivity implements LoginView {

    private EditText textEmail;
    private TextView labelEmail;
    private EditText textPassword;
    private TextView labelPassword;
    private TextView labelLoginForm;
    private ProgressBar progressBarLogin;
    private LoginPresenter loginPresenter;
    private TextView btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fetchComponents();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //================================================================================
    // Built for wrapping all standard pointing to design components
    //================================================================================
    private void fetchComponents() {
        textEmail = findViewById(R.id.text_email);
        textPassword = findViewById(R.id.text_password);
        btnLogin = findViewById(R.id.btn_login);
        labelEmail = findViewById(R.id.label_email);
        labelPassword = findViewById(R.id.label_password);
        progressBarLogin = findViewById(R.id.progress_login);
        labelLoginForm = findViewById(R.id.label_login_form);
        TextView btnRegister = findViewById(R.id.btn_register);


        loginPresenter = new LoginPresenter(this,new LoginInteractor(RetrofitSingleton.getInstance()));
        btnLogin.setOnClickListener(view -> loginPresenter.validateCredentials(textEmail.getText().toString(),textPassword.getText().toString()));
        btnRegister.setOnClickListener(view -> loginPresenter.onNavigateToRegistration());

        textEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                loginPresenter.validateFields(textEmail.getText().toString(), textPassword.getText().toString());
            }
        });

        textPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                loginPresenter.validateFields(textEmail.getText().toString(), textPassword.getText().toString());
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.onDestroy();
    }


    @Override
    public void showProgress() {
        progressBarLogin.setVisibility(View.VISIBLE);
        labelEmail.setVisibility(View.GONE);
        labelPassword.setVisibility(View.GONE);
        labelLoginForm.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBarLogin.setVisibility(View.GONE);
    }

    @Override
    public void hideEmailError() {
        labelEmail.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hidePasswordError() {
        labelPassword.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setEmailError() {
        labelEmail.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPasswordError() {
        labelPassword.setVisibility(View.VISIBLE);
    }

    @Override
    public void setFormError() {
        labelLoginForm.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean isLoggedIn() {
        SharedPreferences preferences = this.getSharedPreferences("user"
                , MODE_PRIVATE);
        return  preferences != null && !preferences.getString("api_token",
                "no_token").equals("no_token");

    }


    //================================================================================
    // Built for storing user details and api_token upon logging in
    //================================================================================
    @Override
    public void setSharedPreference(String key, String jsonString) {
        Context ctx = getApplicationContext();
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("user"
                , MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, jsonString);
        editor.apply();
    }

    @Override
    public void navigateToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void navigateToRegistration() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    @Override
    public void lockLoginButton() {
        btnLogin.setClickable(false);
    }

    @Override
    public void unlockLoginButton() {
        btnLogin.setClickable(true);
    }
}
