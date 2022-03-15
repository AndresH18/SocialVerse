package com.andresd.socialverse.ui.login2.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.andresd.socialverse.databinding.SignUpFragmentBinding;
import com.andresd.socialverse.ui.login.signup.SignUpFragmentArgs;

public class SignUpFragment2 extends Fragment {

    private SignUpViewModel2 mViewModel;
    private SignUpFragmentBinding binding;

    public static SignUpFragment2 newInstance() {
        return new SignUpFragment2();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = SignUpFragmentBinding.inflate(inflater, container, false);

        mViewModel = new ViewModelProvider(this).get(SignUpViewModel2.class);
        // TODO: Use the ViewModel


        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        SignUpFragmentArgs args = SignUpFragmentArgs.fromBundle(getArguments());
        String email = args.getEmail().trim();
        String password = args.getPassword().trim();
        // FIXME: Use the viewModel to set the values
        binding.passwordEditText.setText(password);
        binding.emailEditText.setText(email);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
