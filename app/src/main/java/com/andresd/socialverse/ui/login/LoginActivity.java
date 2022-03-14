package com.andresd.socialverse.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.andresd.socialverse.MainActivity;
import com.andresd.socialverse.R;
import com.andresd.socialverse.databinding.LoginActivityBinding;
import com.andresd.socialverse.ui.login.signin.SignInFragment;


public class LoginActivity extends AppCompatActivity implements SignInFragment.LoginListener {

    //    private LoginViewModel loginViewModel;
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;

    private LoginActivityBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = LoginActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // request login View Model
//        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory()).get(LoginViewModel.class);

        setSupportActionBar(binding.toolbar);

        navController = Navigation.findNavController(this, R.id.login_nav_host_fragment);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController);

    }

    @Override
    public void onLogin() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        setResult(Activity.RESULT_OK);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}