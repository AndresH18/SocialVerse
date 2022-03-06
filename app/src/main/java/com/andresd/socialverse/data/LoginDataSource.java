package com.andresd.socialverse.data;

import com.andresd.socialverse.data.model.LoggedInUser;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 * FIXME: Adjust for firebase user authentication
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
        FirebaseAuth.getInstance().signOut();
    }
}