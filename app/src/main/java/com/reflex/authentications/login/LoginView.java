package com.reflex.authentications.login;

/*
   This interface is a contract for the view,
   it uses
*/
public interface LoginView {


    void showProgress();

    void hideProgress();

    void setUsernameError();

    void setPasswordError();

    void navigateToHome();


}
