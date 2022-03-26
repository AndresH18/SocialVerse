package com.andresd.socialverse;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<UserAuthState> userState = new MutableLiveData<>();
    private String lastUID;

    MainActivityViewModel() {
        // initialize values
        userState.setValue(FirebaseAuth.getInstance().getCurrentUser() == null ? UserAuthState.NOT_LOGGED_IN : UserAuthState.VALID);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            lastUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        // TODO: Add auth state change listener
//        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//            }
//        });
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        userState.setValue(UserAuthState.INVALID); // or SIGNED_OUT
    }

    public MutableLiveData<UserAuthState> getUserState() {
        return userState;
    }
}

enum UserAuthState {
    // TODO: use better states
    VALID,
    INVALID,
    SIGNED_IN,
    /**
     * <p>The user has signed out.</p>
     */
    SIGNED_OUT,
    /**
     * <p>The user has not been logged.</p>
     */
    NOT_LOGGED_IN,
    ;
}
