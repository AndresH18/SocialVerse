package com.andresd.socialverse.ui.login.signin;

import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andresd.socialverse.R;
import com.andresd.socialverse.data.Result;
import com.andresd.socialverse.data.model.LoggedInUser;
import com.andresd.socialverse.data.model.LoginRepository;

public class SignInViewModel extends ViewModel implements LoginRepository.OnLoginResultListener {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

    SignInViewModel() {
    }

    public void signIn(String username, String password) {
        LoginRepository.getInstance().signIn(username, password, this);
    }

    public MutableLiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public MutableLiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    /**
     * TODO: FIXME
     *
     * @param username
     * @param password
     */
    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
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


    @Override
    public void onLoginSuccessful(@NonNull Result.Success<LoggedInUser> result) {
        loginResult.postValue(new LoginResult(new LoggedInUserView(result.getData().getDisplayName())));
    }

    @Override
    public void onLoginFailed(@NonNull Integer error) {
        loginResult.postValue(new LoginResult(error));
    }
}