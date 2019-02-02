package com.reflex.authentications.login;

/*
   This interface is a contract for the view,
   it uses
*/
public interface LoginView {


    void showProgress();

    void hideProgress();

    void hideEmailError();

    void hidePasswordError();

    void setEmailError();

    void setPasswordError();

    void setFormError();

    boolean isLoggedIn();

    void setSharedPreference(String key, String jsonString);

    void navigateToHome();

    void navigateToRegistration();

    void lockLoginButton();

    void unlockLoginButton();

}
