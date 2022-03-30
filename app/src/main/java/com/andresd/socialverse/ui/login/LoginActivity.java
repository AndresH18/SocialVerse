package com.andresd.socialverse.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.andresd.socialverse.ui.main.MainActivity;
import com.andresd.socialverse.R;
import com.andresd.socialverse.databinding.ActivityLoginBinding;

/**
 */
public class LoginActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private NavController navController;

    private ActivityLoginBinding binding;

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory()).get(LoginViewModel.class);

        setSupportActionBar(binding.toolbar);
        navController = Navigation.findNavController(this, R.id.login_nav_host_fragment);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController);

        /* Observe the result of the login */
        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(LoginResult loginResult) {
                switch (loginResult) {
                    case SUCCESSFUL:
                        // sign in successful
                        mainActivity();
                        break;
                    case WRONG_USERNAME:
                        // wrong username
                        showLoginFailed(R.string.result_wrong_username);
                        break;
                    case WRONG_PASSWORD:
                        // wrong password
                        showLoginFailed(R.string.result_wrong_password);
                        break;
                    case NO_NETWORK_CONNECTION:
                        showLoginFailed(R.string.result_no_network_connection);
                    case FAILED:
                        showLoginFailed(R.string.result_failed);
                        break;
                    default:

                        break;
                }
            }
        });

    }
    /*
      FIXME: change name, describir mas la accion de ir a la siguiente actividad
       en lugar de que el usuario inicie sesion.
       Es mas una accion de la aplicacion con la aplicacion que el usuario con la
       aplicacion.
      signIn()
      */
    private void mainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        setResult(Activity.RESULT_OK);
        startActivity(intent);
        finish();
    }
    @SignUpElement
    private void signUp() {
        // TODO
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(this, errorString, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

}
