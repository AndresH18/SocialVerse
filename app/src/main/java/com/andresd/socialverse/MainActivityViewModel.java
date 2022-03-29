package com.andresd.socialverse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andresd.socialverse.data.model.User;
import com.andresd.socialverse.data.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivityViewModel extends ViewModel {
    // some sort of user object?
    //
    private MutableLiveData<User> currentUserLiveData = new MutableLiveData<>();
    private MutableLiveData<UserAuthState> userState = new MutableLiveData<>();
    private String lastUID;

    MainActivityViewModel() {
        // initialize values
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            userState.setValue(UserAuthState.VALID);
            lastUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
//            currentUserLiveData = UserRepository.getUser(FirebaseAuth.getInstance().getCurrentUser().getUid());
            UserRepository.getInstance().getUser(FirebaseAuth.getInstance().getCurrentUser().getUid(), currentUserLiveData);
        } else {
            userState.setValue(UserAuthState.NOT_LOGGED_IN);
        }

        // TODO: Add auth state change listener
//        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                  // use lastUID to check if user changed
//            }
//        });
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        userState.setValue(UserAuthState.INVALID); // or SIGNED_OUT
    }

    public LiveData<UserAuthState> getUserState() {
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
