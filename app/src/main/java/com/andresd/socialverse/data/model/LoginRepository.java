package com.andresd.socialverse.data.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 * FIXME: Adjust for firebase user authentication
 */
public class LoginRepository {

    private static final String TAG = LoginRepository.class.getSimpleName();

    private static volatile LoginRepository instance;
    private MutableLiveData<FirebaseUser> userMutableLiveData = null;


    private LoginRepository() {
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (FirebaseAuth.getInstance().getCurrentUser() == firebaseAuth.getCurrentUser()) {
                    userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                } else {
                    Log.w(TAG, "Detected change to Authentication state that does not resemble on instante");
                }
            }
        });
    }

    public static LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository();
        }
        return instance;
    }

    public void login(String username, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

            }
        });
    }

    public boolean isLogged() {
        return userMutableLiveData.getValue() != null;
    }

    @Deprecated // TODO: Delete
    private void setLoggedInUser(FirebaseUser user) {
        this.userMutableLiveData.postValue(user);
    }

    public void logout() {

        FirebaseAuth.getInstance().signOut();
    }

}
