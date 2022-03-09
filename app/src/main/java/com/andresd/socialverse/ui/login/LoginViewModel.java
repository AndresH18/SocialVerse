package com.andresd.socialverse.ui.login;

import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andresd.socialverse.R;
import com.andresd.socialverse.data.Result;
import com.andresd.socialverse.data.model.LoggedInUser;
import com.andresd.socialverse.data.model.LoginRepository;
import com.google.firebase.auth.FirebaseUser;

/**
 * FIXME: Adjust for firebase user authentication
 */
public class LoginViewModel extends ViewModel implements LoginRepository.OnLoginSuccessfulListener {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

    LoginViewModel() {
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        LoginRepository.getInstance().login(username, password, this);
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

    // A placeholder username validation check TODO
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
    public void onLoginSuccessful(@NonNull Result.Success<FirebaseUser> result) {
        FirebaseUser user = result.getData();
        LoggedInUser data = new LoggedInUser(user.getUid(), user.getDisplayName());
        loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));

    }
}