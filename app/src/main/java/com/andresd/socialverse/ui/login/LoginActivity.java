package com.andresd.socialverse.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.andresd.socialverse.MainActivity;
import com.andresd.socialverse.databinding.LoginActivityBinding;
import com.andresd.socialverse.ui.login.signin.SignInFragment;


public class LoginActivity extends AppCompatActivity implements SignInFragment.LoginListener {

    private LoginViewModel loginViewModel;
    private LoginActivityBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = LoginActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // request loginViewModel
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);


    }

    @Override
    public void onLogin() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        setResult(Activity.RESULT_OK);
        startActivity(intent);
        finish();
    }
}