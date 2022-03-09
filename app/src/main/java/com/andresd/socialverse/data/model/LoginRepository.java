package com.andresd.socialverse.data.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.andresd.socialverse.data.Result;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginRepository {

    private static final String TAG = LoginRepository.class.getSimpleName();

    private static volatile LoginRepository instance;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser;

    // private constructor : singleton access
    private LoginRepository() {
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mAuth.getCurrentUser() == null) {
                    currentUser = null;
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

    public void login(String username, String password, OnLoginSuccessfulListener onLoginSuccessfulListener) {
        Task<AuthResult> loginTask = mAuth
                .signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            currentUser = mAuth.getCurrentUser();
                            onLoginSuccessfulListener.onLoginSuccessful(new Result.Success<>(currentUser));
                            Log.d(TAG, "User successfully signed in.");
                        } else {
                            Log.w(TAG, "Failed to sign in user.");
                        }
                    }
                });

    }

    public void signOut() {
        mAuth.signOut();

    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }


    public interface OnLoginSuccessfulListener {
        void onLoginSuccessful(@NonNull Result.Success<FirebaseUser> result);
    }
}
