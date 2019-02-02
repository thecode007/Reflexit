package com.reflex.authentications.registration;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.reflex.util.Clickable;


public class RegistrationViewModel extends ViewModel {

    public MutableLiveData<Boolean> isLoading;
    public MutableLiveData<String> email;
    public MutableLiveData<String> emailError;
    public MutableLiveData<String> name;
    public MutableLiveData<String> nameError;
    public MutableLiveData<String> password;
    public MutableLiveData<String> passwordError;
    public MutableLiveData<Boolean> isValidPassword;
    public MutableLiveData<String> passwordConfirm;
    public MutableLiveData<String> passwordConfirmError;
    public MutableLiveData<Boolean> isValidPasswordConfirm;
    public MutableLiveData<Boolean> isValidEmail;
    public MutableLiveData<Boolean> isValidForm;
    public MutableLiveData<Boolean> isValidName;

    public RegistrationViewModel() {
        isLoading = new MutableLiveData<>();
        isLoading.setValue(false);
        name = new MutableLiveData<>();
        nameError = new MutableLiveData<>();
        email = new MutableLiveData<>();
        emailError = new MutableLiveData<>();
        password = new MutableLiveData<>();
        isValidPassword = new MutableLiveData<>();
        isValidEmail = new MutableLiveData<>();
        passwordConfirm = new MutableLiveData<>();
        isValidPasswordConfirm = new MutableLiveData<>();
        isValidForm = new MutableLiveData<>();
        isValidName = new MutableLiveData<>();
        passwordError = new MutableLiveData<>();
        passwordConfirmError = new MutableLiveData<>();
    }
}
