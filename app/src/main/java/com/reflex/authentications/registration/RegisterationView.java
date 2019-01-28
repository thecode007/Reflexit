package com.reflex.authentications.registration;

public interface RegisterationView {

    void showProgress();

    void hideProgress();

    void setEmailError();

    void setPasswordError();

    void setUserNameError();

    void setPasswordConfirmError();

    void setFormError();

    void setSharedPreference(String key, String jsonString);

    void navigateToHome();

}
