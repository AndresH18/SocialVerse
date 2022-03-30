package com.andresd.socialverse;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andresd.socialverse.data.model.AbstractGroup;
import com.andresd.socialverse.data.model.AbstractUser;
import com.andresd.socialverse.data.repository.GroupRepository;
import com.andresd.socialverse.data.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;


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

public class MainActivityViewModel extends ViewModel {
    // some sort of user object?
    //
    private MutableLiveData<AbstractUser> currentUser = new MutableLiveData<>();
    private MutableLiveData<List<AbstractGroup>> searchGroups = new MutableLiveData<>();

    private MutableLiveData<UserAuthState> userState = new MutableLiveData<>();
    private String lastUID;

    private MutableLiveData<String> mHomeText = new MutableLiveData<>("This is Home Fragment");

    MainActivityViewModel() {
        // initialize values
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            userState.setValue(UserAuthState.VALID);
            lastUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
//            currentUserLiveData = UserRepository.getUser(FirebaseAuth.getInstance().getCurrentUser().getUid());
            UserRepository.getInstance().getUser(FirebaseAuth.getInstance().getCurrentUser().getUid(), currentUser);
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

    public void searchGroupByName(@NonNull String groupName) {
        GroupRepository.getInstance().searchGroupByName(groupName, searchGroups);
    }

    public void searchGroupsByTags(@NonNull String tags) {
        List<String> tagsList = Arrays.asList(tags.replaceAll(" ", "").split(","));
        GroupRepository.getInstance().searchGroupsByTags(tagsList, searchGroups);
    }

    public LiveData<List<AbstractGroup>> getSearchGroups() {
        return searchGroups;
    }

    public LiveData<UserAuthState> getUserState() {
        return userState;
    }

    public LiveData<String> getText() {
        return mHomeText;
    }
}
