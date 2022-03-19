package com.andresd.socialverse.ui.login;

import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andresd.socialverse.R;
import com.andresd.socialverse.data.model.LoggedInUser;
import com.andresd.socialverse.data.model.LoginRepository;

public class LoginViewModel extends ViewModel {
    /* TODO
        Esto se puede cambiar cuando se sepan de hilos, para asi poder usar el LoginRepository sin necesidad
        de que contenga livedata.
        Tambien Revisar como poner SignInViewModel y SignUpViewModel dentro de LoginViewModel.
     */
    private MutableLiveData<LoggedInUser> loggedInUser = LoginRepository.getInstance().getUser();
    private MutableLiveData<SignInFormState> signInFormState = new MutableLiveData<>();


    public void signIn(@NonNull String username, @NonNull String password) {
        LoginRepository.getInstance().signIn(username, password);
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            signInFormState.setValue(new SignInFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            signInFormState.setValue(new SignInFormState(null, R.string.invalid_password));
        } else {
            signInFormState.setValue(new SignInFormState(true));
        }
    }

    // A placeholder username validation check
    // TODO : Check for correct in case email address format
    // FIXME: it is checking if it is a username or an email, should it only be email?
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    public MutableLiveData<LoggedInUser> getLoggedInUser() {
        return loggedInUser;
    }

    public MutableLiveData<SignInFormState> getSignInFormState() {
        return signInFormState;
    }
}
