package com.andresd.socialverse.ui.login;

import androidx.annotation.Nullable;

import java.util.Map;

/**
 * Data validation state of the Sign Up form.
 */
@SignUpElement
final class SignUpFormState {

    public static final String FIRST_NAME_ERROR_KEY = "first_name";
    public static final String LAST_NAME_ERROR_KEY = "last_name";
    public static final String EMAIL_ERROR_KEY = "email";
    public static final String PASSWORD_ERROR_KEY = "password";

    @Nullable
    private Integer firstNameError;
    @Nullable
    private Integer lastNameError;
    @Nullable
    private Integer emailError;
    @Nullable
    private Integer passwordError;

    private boolean isDataValid;

    public SignUpFormState(Map<String, Integer> map) {
        firstNameError = map.get(FIRST_NAME_ERROR_KEY);
        lastNameError = map.get(LAST_NAME_ERROR_KEY);
        emailError = map.get(EMAIL_ERROR_KEY);
        passwordError = map.get(PASSWORD_ERROR_KEY);
/*        if (firstNameError == null && lastNameError == null
                && emailError == null && passwordError == null) {
            isDataValid = true;
        }*/
        isDataValid = (firstNameError == null && lastNameError == null
                && emailError == null && passwordError == null);
    }

    @Nullable
    public Integer getFirstNameError() {
        return firstNameError;
    }

    @Nullable
    public Integer getLastNameError() {
        return lastNameError;
    }

    @Nullable
    public Integer getEmailError() {
        return emailError;
    }

    @Nullable
    public Integer getPasswordError() {
        return passwordError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}
