package com.andresd.socialverse.ui.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andresd.socialverse.data.model.LoggedInUser;
import com.andresd.socialverse.data.model.LoginRepository;

public class LoginViewModel extends ViewModel {
    /* TODO
        Esto se puede cambiar cuando se sepan de hilos, para asi poder usar el LoginRepository sin necesidad
        de que contenga livedata.
        Tambien Revisar como poner SignInViewModel y SignUpViewModel dentro de LoginViewModel.
     */
    private MutableLiveData<LoggedInUser> loggedInUser = LoginRepository.getInstance().getUser();

    public MutableLiveData<LoggedInUser> getLoggedInUser() {
        return loggedInUser;
    }


}
