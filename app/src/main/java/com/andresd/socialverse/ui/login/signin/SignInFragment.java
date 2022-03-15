package com.andresd.socialverse.ui.login.signin;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.andresd.socialverse.R;
import com.andresd.socialverse.databinding.SignInFragmentBinding;

public class SignInFragment extends Fragment {

    private static final String TAG = SignInFragment.class.getSimpleName();

    private SignInViewModel mViewModel;
    private SignInFragmentBinding binding;

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(SignInViewModel.class);
        binding = SignInFragmentBinding.inflate(inflater, container, false);


        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button signInButton = binding.login;
        final Button signUpButton = binding.signUpButton;
        final ProgressBar loadingProgressBar = binding.loading;

        /* Check that the credentials are correctly formatted */
        mViewModel.getSignInFormState().observe(this, new Observer<SignInFormState>() {
            @Override
            public void onChanged(@Nullable SignInFormState signInFormState) {
                if (signInFormState == null) {
                    return;
                }
                signInButton.setEnabled(signInFormState.isDataValid());
                if (signInFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(signInFormState.getUsernameError()));
                }
                if (signInFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(signInFormState.getPasswordError()));
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

    private void updateUiWithUser(String string) {
        String welcome = getString(R.string.welcome) + string;
        Toast.makeText(getActivity(), welcome, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
