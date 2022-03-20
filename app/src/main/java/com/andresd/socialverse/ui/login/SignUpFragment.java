package com.andresd.socialverse.ui.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.andresd.socialverse.databinding.SignUpFragmentBinding;

/**
 * TODO: Create Forms
 */
public class SignUpFragment extends Fragment {

    private LoginViewModel mViewModel;
    private SignUpFragmentBinding binding;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = SignUpFragmentBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(requireActivity(), new LoginViewModelFactory()).get(LoginViewModel.class);
        // TODO: Use the ViewModel

        final Button signUpButton = binding.signUpButton;
        final EditText firstNameEditText = binding.firstNameEditText;
        final EditText lastNameEditText = binding.lastNameEditText;
        final EditText emailEditText = binding.emailEditText;
        final EditText passwordEditText = binding.passwordEditText;

        /* Check that the information in the form is correctly formatted */
        mViewModel.getSignUpFormState().observe(getViewLifecycleOwner(), new Observer<SignUpFormState>() {
            @Override
            public void onChanged(SignUpFormState signUpFormState) {
                if (signUpFormState == null) {
                    return;
                }
                signUpButton.setEnabled(signUpFormState.isDataValid());
                if (signUpFormState.getFirstNameError() != null) {
                    firstNameEditText.setError(getString(signUpFormState.getFirstNameError()));
                }
                if (signUpFormState.getLastNameError() != null) {
                    lastNameEditText.setError(getString(signUpFormState.getLastNameError()));
                }
                if (signUpFormState.getEmailError() != null) {
                    emailEditText.setError(getString(signUpFormState.getEmailError()));
                }
                if (signUpFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(signUpFormState.getPasswordError()));
                }
            }
        });

        /* Creating the Text Change Listener */
        // TODO: Mirar si mejor agregar la funcionalidad al boton
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
                mViewModel.signUpDataChanged(firstNameEditText.getText().toString(),
                        lastNameEditText.getText().toString(),
                        emailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };

        /* Adding the Text Change Listeners to the EditTexts */
        firstNameEditText.addTextChangedListener(afterTextChangedListener);
        lastNameEditText.addTextChangedListener(afterTextChangedListener);
        emailEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        /* add onClickListener to the SignUp Button */
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.signUp(firstNameEditText.getText().toString(),
                        lastNameEditText.getText().toString(),
                        emailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        SignUpFragmentArgs args = SignUpFragmentArgs.fromBundle(getArguments());
        String email = args.getEmail().trim();
        String password = args.getPassword().trim();
        // FIXME: Use the viewmodel for the
        binding.passwordEditText.setText(password);
        binding.emailEditText.setText(email);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}