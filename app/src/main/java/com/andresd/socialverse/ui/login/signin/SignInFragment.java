package com.andresd.socialverse.ui.login.signin;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.andresd.socialverse.R;
import com.andresd.socialverse.databinding.SignInFragmentBinding;

public class SignInFragment extends Fragment {

    private static final String TAG = SignInFragment.class.getSimpleName();

    private LoginListener loginCallback;
    private SignInViewModel mViewModel;
    private SignInFragmentBinding binding;


    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this, new SignInViewModelProvider())
                .get(SignInViewModel.class);
        binding = SignInFragmentBinding.inflate(inflater, container, false);


        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button signInButton = binding.login;
        final Button signUpButton = binding.signUpButton;
        final ProgressBar loadingProgressBar = binding.loading;


        /* Check that the credentials are correctly formatted */
        mViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                signInButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        /* add an action for the  result of the login */
        mViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                    if (loginCallback != null) {
                        loginCallback.onLogin();
                    } else {
                        Log.e(TAG, "onChanged: loginCallback is null");
                    }
                }
            }
        });

        /* Password and User TextFields Change Listeners */
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                mViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        /* Adding the text listeners to the username and password editText */
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        /* Add 'next' button action to Done in order to login */
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    mViewModel.signIn(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        /* add on click listener to signIn button */
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                mViewModel.signIn(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });

        /* add on click listener to signUp button */
        /* this is another way of creating the listener
        signUpButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.signinToSignup));

        or using safeargs

        SignInFragmentDirections.SigninToSignup action = SignInFragmentDirections.signinToSignup();
        action.setEmail(usernameEditText.getText().toString());
        action.setPassword(passwordEditText.getText().toString());
        signUpButton.setOnClickListener(Navigation.createNavigateOnClickListener(action));

         */
        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.signinToSignup);
                // using safe args
                SignInFragmentDirections.SigninToSignup action = SignInFragmentDirections.signinToSignup();
                action.setEmail(usernameEditText.getText().toString());
                action.setPassword(passwordEditText.getText().toString());
                Navigation.findNavController(v).navigate(action);
                // TODO: TRY USING NO DEFAULT VALUE AND ALLOWING FOR NULL, EDITTEXT SHOULD USE EMPTY IF
                //  A NULL IS PASSED IN setText()
            }
        });


        // TESTING: Delete test user and password
        binding.username.setText("testing@socialverse.test");
        binding.password.setText("socialTest");

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SignInViewModel.class);
        // TODO: Use the ViewModel
    }


    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getActivity(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getActivity(), errorString, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            loginCallback = (LoginListener) getActivity();
        } catch (ClassCastException classCastException) {
            Log.e(TAG, "onAttach: activity must implement" + LoginListener.class.getSimpleName(), classCastException);
            throw new ClassCastException(getActivity().toString() + " must implement " + LoginListener.class.getSimpleName());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public interface LoginListener {
        void onLogin();
    }

}