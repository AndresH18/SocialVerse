package com.andresd.socialverse.data.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.andresd.socialverse.data.Result;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginRepository {

    private static final String TAG = LoginRepository.class.getSimpleName();

    private static volatile LoginRepository instance;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private LoggedInUser user;

    // private constructor : singleton access
    private LoginRepository() {
        if (mAuth.getCurrentUser() != null) {
            user = LoggedInUser.createUserFromFirebase(mAuth.getCurrentUser());
        }
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mAuth.getCurrentUser() == null) {
                    user = null;
                    // TODO: Notify SignOut event

                } else if (user == null) {
                    user = LoggedInUser.createUserFromFirebase(mAuth.getCurrentUser());
                    // TODO: Notify login event?
                    //  Should this be done here considering that the #login will handle the login logic?
                } else if (!user.getUserId().equals(mAuth.getCurrentUser().getUid())) {
                    // TODO: Notify user changed event
                    user = LoggedInUser.createUserFromFirebase(mAuth.getCurrentUser());
                }

            }
        });
    }

    public static LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository();
        }
        return instance;
//        return instance == null ? instance = new LoginRepository() : instance;
    }

    public void login(@NonNull String username, @NonNull String password, @NonNull OnLoginSuccessfulListener onLoginSuccessfulListener) {
        Task<AuthResult> loginTask = mAuth
                .signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (mAuth.getCurrentUser() != null) {
                                user = LoggedInUser.createUserFromFirebase(mAuth.getCurrentUser());
                                onLoginSuccessfulListener.onLoginSuccessful(new Result.Success<>(user));
                                Log.d(TAG, "User successfully signed in.");
                            } else {
                                Log.e(TAG, "onComplete: LoginTask successful but user is null");
                            }
                        } else {
                            Log.w(TAG, "Failed to sign in user.");
                        }
                    }
                });

    }

    public void signOut() {
        mAuth.signOut();

    }

    public LoggedInUser getUser() {
        return user;
    }

    public interface OnLoginSuccessfulListener {
        void onLoginSuccessful(@NonNull Result.Success<LoggedInUser> result);
    }

    public interface OnSignOutListener {
        void onSignOut();
    }
}
