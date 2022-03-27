package com.andresd.socialverse.ui.login;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.andresd.socialverse.databinding.FragmentSignInBinding;

public class SignInFragment extends Fragment {

    private static final String TAG = SignInFragment.class.getSimpleName();

    private LoginViewModel mViewModel;
    private FragmentSignInBinding binding;

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity(), new LoginViewModelFactory()).get(LoginViewModel.class);
        binding = FragmentSignInBinding.inflate(inflater, container, false);


        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button signInButton = binding.login;
        final Button signUpButton = binding.signUpButton;
        final ProgressBar loadingProgressBar = binding.loading;


        /* Check result from the sign */
        mViewModel.getLoginResult().observe(getViewLifecycleOwner(), new Observer<LoginResult>() {
            @Override
            public void onChanged(LoginResult loginResult) {
                // remove the loadingProgressBar
                loadingProgressBar.setVisibility(View.GONE);
            }
        });

        /* Check that the credentials are correctly formatted */
        mViewModel.getSignInFormState().observe(getViewLifecycleOwner(), new Observer<SignInFormState>() {
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
                mViewModel.signInDataChanged(usernameEditText.getText().toString(),
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
                // TODO: make sure that the form state is valid
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loadingProgressBar.setVisibility(View.VISIBLE);
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
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.signinToSignup);
                final String email = usernameEditText.getText().toString().trim();
                final String password = passwordEditText.getText().toString().trim();
                // using safe args
                SignInFragmentDirections.SigninToSignup action = SignInFragmentDirections.signinToSignup(email, password);
//                action.setEmail(usernameEditText.getText().toString());
//                action.setPassword(passwordEditText.getText().toString());
                Navigation.findNavController(v).navigate(action);
            }
        });

        // TODO: Delete test user and password
        binding.username.setText("testing@socialverse.test");
        binding.password.setText("socialTest");

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
