package com.reflex.authentications.registration;

import android.arch.lifecycle.LifecycleOwner;

/**
 * Built for avoiding memory leaks when using
 * lifecycleOwner specially activities
 */
public interface RegistrationView extends LifecycleOwner {

    /**
     * @param key key for add in shared prefs map
     * @param jsonString the value of the key
     */
    void saveToPrefs(String key, String jsonString);

    /**
     * navigates to home view
     */
    void navigateToHome();
}
