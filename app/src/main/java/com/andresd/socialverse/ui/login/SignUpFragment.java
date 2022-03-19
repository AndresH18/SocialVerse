package com.andresd.socialverse.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

        mViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        // TODO: Use the ViewModel


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

}