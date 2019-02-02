package com.reflex.authentications.registration;

import android.arch.lifecycle.LifecycleOwner;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.reflex.util.ResponseCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Built for controlling UI interactions and its logic
 * setting live observers, validating fields responses
 * getting online data from the interactor class
 */
public class RegistrationPresenter {

    private RegistrationViewModel viewModel;
    private RegistrationView owner;

    /**
     *  @param  viewModel  represents data hooked to the owner view
     *  @param  owner representing the owner of the model which lifecycle depends on it
     */
    RegistrationPresenter(RegistrationViewModel viewModel, RegistrationView owner) {
        this.viewModel = viewModel;
        this.owner = owner;
        initOwnerObservers();
    }

    /**
     * checks for format validity to all form
     *  @param  viewModel  the androidModel representing the data hooked to the owner view
     */
    private void checkFormValidity(RegistrationViewModel viewModel) {
        // handling empty form
        if (viewModel.isValidPassword.getValue() == null ||
                viewModel.isValidPasswordConfirm.getValue() == null ||
                viewModel.isValidName.getValue() == null ||
                viewModel.isValidEmail.getValue() == null) {
            return;
        }
        // composing all field flags to set form validity
        viewModel.isValidForm.setValue(viewModel.isValidPassword.getValue() &&
                viewModel.isValidPasswordConfirm.getValue() &&
                viewModel.isValidName.getValue() &&
                viewModel.isValidEmail.getValue());
    }

    /**
     * hooking observers to the viewModel's data fields
     */
    private void initOwnerObservers() {
        // regex for valid email format
        String regex = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);

        // hooking email observer
        viewModel.email.observe(owner, email -> {
            // handling empty email
            if (email == null || TextUtils.isEmpty(viewModel.email.getValue())) {
                viewModel.emailError.setValue("This Field is required");
                viewModel.isValidEmail.setValue(false);
                checkFormValidity(viewModel);
                return;
            }

            // handling invalid format
            if (!pattern.matcher(email).matches()) {
                viewModel.emailError.setValue("Invalid Email Format");
                viewModel.isValidEmail.setValue(false);
                checkFormValidity(viewModel);
                return;
            }

            // using interactor for checking
            // the uniqueness of the email
            RegistrationInteractor.checkEmail(email, new ResponseCallBack() {
                @Override
                public void onSuccess(JSONObject data) {
                    viewModel.isValidEmail.setValue(true);
                    checkFormValidity(viewModel);
                }
                @Override
                public void onFail(String error) {
                    viewModel.emailError.setValue(error);
                    viewModel.isValidEmail.setValue(false);
                    checkFormValidity(viewModel);
                }
            });
        });

        // hooking name observer
        viewModel.name.observe(owner, name -> {
            // handling empty name
            if (TextUtils.isEmpty(viewModel.name.getValue())) {
                viewModel.nameError.setValue("This Field is required");
                viewModel.isValidName.setValue(false);
                checkFormValidity(viewModel);
                return;
            }

            // using interactor for checking
            // the uniqueness of the name
            RegistrationInteractor.checkName(name, new ResponseCallBack() {
                @Override
                public void onSuccess(JSONObject data) {
                    viewModel.isValidName.setValue(true);
                    checkFormValidity(viewModel);
                }

                @Override
                public void onFail(String errorMessage) {
                    viewModel.nameError.setValue(errorMessage);
                    viewModel.isValidName.setValue(false);
                    checkFormValidity(viewModel);
                }
            });
        });

        // hooking password observer
        viewModel.password.observe(owner, s -> {
            // handling empty password
            if (TextUtils.isEmpty(viewModel.password.getValue())) {
                viewModel.passwordError.setValue("This Field is required");
                viewModel.isValidPassword.setValue(false);
                checkFormValidity(viewModel);
                return;
            }

            // handling passwordConfirm mismatch
            if (!TextUtils.isEmpty(viewModel.passwordConfirm.getValue()) &&
                    !viewModel.passwordConfirm.getValue()
                            .equals(viewModel.password.getValue())){

                viewModel.passwordConfirmError.setValue("Password mismatch");
                viewModel.isValidPasswordConfirm.setValue(false);
                checkFormValidity(viewModel);
                return;
            }

            // getting through all the above conditions
            // indicates valid password
            viewModel.isValidPassword.setValue(true);
            viewModel.isValidPasswordConfirm.setValue(true);
            checkFormValidity(viewModel);
        });

        // hooking passwordConfirm observer
        viewModel.passwordConfirm.observe(owner, s -> {
            // handling empty passwordConfirm and password mismatch
            if (!TextUtils.isEmpty(viewModel.passwordConfirm.getValue()) &&
                    !viewModel.passwordConfirm.getValue()
                            .equals(viewModel.password.getValue())){

                viewModel.passwordConfirmError.setValue("Password mismatch");
                viewModel.isValidPasswordConfirm.setValue(false);
                checkFormValidity(viewModel);
                return;
            }

            // getting through all the above conditions
            // indicates valid password match
            viewModel.isValidPassword.setValue(true);
            viewModel.isValidPasswordConfirm.setValue(true);
            checkFormValidity(viewModel);
        });
    }

    /**
     * handles the register action
     */
    void register() {
        // handles invalid form
        if (viewModel.isValidForm == null || !viewModel.isValidForm.getValue()) {
            return;
        }
        // handles the registration request using an interactor
        RegistrationInteractor.sendRegisterRequest(viewModel.email.getValue(),
                viewModel.name.getValue(), viewModel.password.getValue(),
                viewModel.passwordConfirm.getValue(), new ResponseCallBack() {
                    @Override
                    public void onSuccess(JSONObject data) throws JSONException {
                        owner.saveToPrefs("user_config",data.getJSONObject("data").toString());
                        owner.saveToPrefs("id",data.getJSONObject("data").getString("id"));
                        owner.saveToPrefs("api_token",data.getString("api_token"));
                        owner.navigateToHome();
                    }
                    @Override
                    public void onFail(String errorMessage) {
                        Log.wtf("Registration error",errorMessage);
                    }
                });

    }
}
