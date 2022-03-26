package com.andresd.socialverse.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * ViewModel provider factory to instantiate {@link LoginViewModel}.
 * Required given {@link LoginViewModel} has a non-empty constructor
 */
public class LoginViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel();
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }

    }
}
