package com.andresd.socialverse.ui.login2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.andresd.socialverse.MainActivity;
import com.andresd.socialverse.R;
import com.andresd.socialverse.data.model.LoggedInUser;
import com.andresd.socialverse.databinding.LoginActivityBinding;

public class LoginActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private NavController navController;

    private LoginActivityBinding binding;

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = LoginActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        setSupportActionBar(binding.toolbar);

        navController = Navigation.findNavController(this, R.id.login_nav_host_fragment);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController);


        loginViewModel.getLoggedInUser().observe(this, new Observer<LoggedInUser>() {
            @Override
            public void onChanged(LoggedInUser loggedInUser) {
                if (loggedInUser != null) {
                    signIn();
                }
            }
        });

    }

    private void signIn() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        setResult(Activity.RESULT_OK);
        startActivity(intent);
        finish();
    }

    private void signUp() {
        // TODO
    }


    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
