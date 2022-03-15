package com.andresd.socialverse.ui.login2;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andresd.socialverse.data.model.LoggedInUser;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoggedInUser> loggedInUser = new MutableLiveData<>();

    public MutableLiveData<LoggedInUser> getLoggedInUser() {
        return loggedInUser;
    }
}
