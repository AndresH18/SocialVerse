package com.andresd.socialverse.ui.login;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.andresd.socialverse.databinding.FragmentSignInBinding;

public class SignInFragment extends Fragment {

    private static final String TAG = SignInFragment.class.getName();

    private LoginViewModel mViewModel;
    private FragmentSignInBinding binding;

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: started");
        binding = FragmentSignInBinding.inflate(inflater, container, false);
        Log.i(TAG, "onCreateView: finished");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState); // not required because super doesn't contain anything
        Log.i(TAG, "onViewCreated: started");

        // create the viewModel
        mViewModel = new ViewModelProvider(requireActivity(), new LoginViewModelFactory()).get(LoginViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button signInButton = binding.login;
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


        // TODO: Delete test user and password
        binding.username.setText("developing@socialverse.test");
        binding.password.setText("developTest");

        Log.i(TAG, "onViewCreated: finished");
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: started");

        Log.i(TAG, "onCreate: finished");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: started");

        Log.i(TAG, "onStart: finished");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: started");

        Log.i(TAG, "onResume: finished");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: started");

        Log.i(TAG, "onPause: finsihed");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: started");

        Log.i(TAG, "onDestroyView: finished");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: started");
        binding = null;
        Log.i(TAG, "onDestroy: finished");
    }
}
