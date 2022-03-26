package com.andresd.socialverse;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivityViewModel extends ViewModel {

    // TODO: Create a livedata that holds the state of the user (sign in.....)
    private MutableLiveData<UserState> userState = new MutableLiveData<>();

    MainActivityViewModel() {
        userState.setValue(FirebaseAuth.getInstance().getCurrentUser() != null ? UserState.VALID : UserState.INVALID);

    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        // TODO: sign user out, notify some livedata of user changed(user = null);
        userState.setValue(UserState.INVALID); // or SIGNED_OUT

    }

    public MutableLiveData<UserState> getUserState() {
        return userState;
    }
}

enum UserState {
    VALID,
    INVALID,
    SIGNED_IN,
    SIGNED_OUT,
    ;
}
