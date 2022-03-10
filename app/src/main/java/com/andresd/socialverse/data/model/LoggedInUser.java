package com.andresd.socialverse.data.model;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;

/**
 * Data class that captures user information for logged-in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String displayName;

    public LoggedInUser(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    private LoggedInUser() {

    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static LoggedInUser createUserFromFirebase(@NonNull FirebaseUser user) {
        LoggedInUser loggedInUser = new LoggedInUser();
        loggedInUser.userId = user.getUid();
        loggedInUser.displayName = user.getDisplayName();
        return loggedInUser;
    }

}