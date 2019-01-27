package com.reflex.authentications.login;


/**
 * Built for handling user interactions with the LoginActivity
 * */

public class LoginPresenter implements  LoginInteractor.OnLoginDoneListener{

    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenter( LoginView loginView, LoginInteractor loginInteractor ) {
        this.loginView = loginView;
        this.loginInteractor = loginInteractor;
    }

    public void validateCredentials(String username, String password) {
        if (loginView != null) {
            loginView.showProgress();
        }
        loginInteractor.login(username, password, this);
    }

    public void onDestroy() {
        loginView = null;
    }


    @Override
    public void onInvalidUsername() {
        if (loginView != null) {
            loginView.setUsernameError();
            loginView.hideProgress();
        }
    }

    @Override
    public void onInvalidPassword() {
        if (loginView != null) {
            loginView.setPasswordError();
            loginView.hideProgress();
        }
    }

    @Override
    public void onSuccess() {
        if (loginView != null) {
            loginView.navigateToHome();
        }
    }
}
