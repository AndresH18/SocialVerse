package com.andresd.socialverse.ui.login.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.andresd.socialverse.databinding.SignUpFragmentBinding;

public class SignUpFragment extends Fragment {

    private SignUpViewModel mViewModel;
    private SignUpFragmentBinding binding;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = SignUpFragmentBinding.inflate(inflater, container, false);

        mViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        // TODO: Use the ViewModel


        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        SignUpFragmentArgs args = SignUpFragmentArgs.fromBundle(getArguments());
        String email = args.getEmail();
        String password = args.getPassword();
        // FIXME: Use the viewmodel
        binding.passwordEditText.setText(password);
        binding.emailEditText.setText(email);
    }

}