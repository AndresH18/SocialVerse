package com.andresd.socialverse.ui.login;

import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andresd.socialverse.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import java.util.HashMap;
import java.util.Map;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<SignInFormState> signInFormState = new MutableLiveData<>();
    private MutableLiveData<SignUpFormState> signUpFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>(LoginResult.NO_ACTION);

    private Map<String, Integer> signUpErrorMap = new HashMap<>();

    LoginViewModel() {
        //
    }

    public void signIn(@NonNull String username, @NonNull String password) {
//        LoginRepository.getInstance().signIn(username, password);
        FirebaseAuth.getInstance().signInWithEmailAndPassword(username, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        loginResult.postValue(LoginResult.SUCCESSFUL);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof FirebaseAuthInvalidUserException) {
                            loginResult.postValue(LoginResult.WRONG_USERNAME);
                        } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            loginResult.postValue(LoginResult.WRONG_PASSWORD);
                        } else if (e instanceof FirebaseNetworkException) {
                            loginResult.postValue(LoginResult.NO_NETWORK_CONNECTION);
                        } else {
                            loginResult.postValue(LoginResult.FAILED);
                        }
                    }
                });
    }

    public void signUp(@NonNull String firstName, @NonNull String lastName, @NonNull String email, @NonNull String password) {
        // TODO:
        //  Talves remplazar firstName y lastName por Nombre?
        //  | IMPLEMENT SIGN UP WITH FIREBASE
        //  | or delete sign up
    }

    public void signInDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            signInFormState.setValue(new SignInFormState(R.string.invalid_username, null));
        } else if (isPasswordInvalid(password)) {
            signInFormState.setValue(new SignInFormState(null, R.string.invalid_password));
        } else {
            signInFormState.setValue(new SignInFormState(true));
        }
    }

    public void signUpDataChanged(@NonNull String firstName, @NonNull String lastName,
                                  @NonNull String email, @NonNull String password) {

        signUpErrorMap.clear();

        if (isNameInvalid(firstName)) {
            signUpErrorMap.put(SignUpFormState.FIRST_NAME_ERROR_KEY, R.string.invalid_first_name);
        }
        if (isNameInvalid(lastName)) {
            signUpErrorMap.put(SignUpFormState.LAST_NAME_ERROR_KEY, R.string.invalid_last_name);
        }
        if (!isEmailValid(email)) {
            signUpErrorMap.put(SignUpFormState.EMAIL_ERROR_KEY, R.string.invalid_email);
        }
        if (isPasswordInvalid(password)) {
            signUpErrorMap.put(SignUpFormState.PASSWORD_ERROR_KEY, R.string.invalid_password);
        }
        signUpFormState.setValue(new SignUpFormState(signUpErrorMap));
    }

    // A placeholder username validation check
    // TODO : Check for correct in case email address format
    // FIXME: it is checking if it is a username or an email, should it only be email?
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    private boolean isNameInvalid(@NonNull String name) {
        name = name.trim();
        return name.isEmpty() || name.length() < 2 || !name.matches("[a-zA-Z]+");
    }

    private boolean isEmailValid(@NonNull String email) {
        email = email.trim();
        // should use eia.edu.co ???
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // A placeholder password validation check
    private boolean isPasswordInvalid(@NonNull String password) {
        return password.trim().length() <= 5;
    }

    public MutableLiveData<SignUpFormState> getSignUpFormState() {
        return signUpFormState;
    }

    public MutableLiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public MutableLiveData<SignInFormState> getSignInFormState() {
        return signInFormState;
    }
}
