package com.andresd.socialverse.data.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Class that intended to use livedata objects in order to observe user
 */
public class LoginRepository {

    private static final String TAG = LoginRepository.class.getSimpleName();
    private static volatile LoginRepository instance;

    private final MutableLiveData<LoggedInUser> user = new MutableLiveData<>();

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();


    public static LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository();
        }
        return instance;
    }

    // private constructor : singleton
    private LoginRepository() {
        if (mAuth.getCurrentUser() != null) {
            user.postValue(LoggedInUser.createUserFromFirebase(mAuth.getCurrentUser()));
        }
        /*
         FIXME: check if this listener is executing
          also decide if it's going to be used, or passed all the required initialization
          to the constructor
        */
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mAuth.getCurrentUser() == null) {
                    // no user sign in
                    user.postValue(null);

                } else if (user.getValue() == null) {
                    // Firebase has a user but there is not in LoggedInUser
                    user.postValue(LoggedInUser.createUserFromFirebase(mAuth.getCurrentUser()));

                } else if (!user.getValue().getUserId().equals(mAuth.getCurrentUser().getUid())) {
                    // logged user is different from FirebaseUser
                    user.postValue(LoggedInUser.createUserFromFirebase(mAuth.getCurrentUser()));
                }
            }
        });
    }

    public void signIn(@NonNull String email, @NonNull String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // check if task was successful
                            if (mAuth.getCurrentUser() != null) {
                                // check if FirebaseAut has a user sign in
                                LoggedInUser loggedInUser = LoggedInUser.createUserFromFirebase(mAuth.getCurrentUser());
                                user.postValue(loggedInUser);
//                                onLoginResultListener.onLoginSuccessful(new Result.Success<>(loggedInUser));
                                Log.d(TAG, "onComplete: LoginTask successful");
                            } else {
                                Log.w(TAG, "onComplete: Failed to sign in user");
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Login failed", e);
                        // if (e instanceof FirebaseAuthInvalidUserException
                        // Notify login failed
//                        onLoginResultListener.onLoginFailed(R.string.login_failed);
                    }
                });
    }

    public void signUp() {
        // TODO
    }

    public void signOut() {
        mAuth.signOut();
        user.postValue(null);
    }

    public MutableLiveData<LoggedInUser> getUser() {
        return user;
    }

//    public interface OnLoginResultListener {
//        void onLoginSuccessful(@NonNull Result.Success<LoggedInUser> result);
//
//        void onLoginFailed(@NonNull Integer error);
//    }
}
