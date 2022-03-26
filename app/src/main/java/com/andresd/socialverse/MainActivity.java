package com.andresd.socialverse;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.andresd.socialverse.databinding.MainActivityBinding;
import com.andresd.socialverse.ui.login.LoginActivity;

/**
 * <b>Main Activity</b>
 * <p>This Class is the Controller for the main activity.<br>
 * It manages the interaction between the {@link com.andresd.socialverse.ui.main.home.HomeFragment},
 * {@link com.andresd.socialverse.ui.main.search.SearchFragment} and {@link com.andresd.socialverse.ui.main.groups.GroupsFragment};
 * as well as the main {@link androidx.appcompat.widget.Toolbar} and
 * {@link com.google.android.material.bottomnavigation.BottomNavigationView}.</p>
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Logcat tag
     */
    private static final String TAG = MainActivity.class.getSimpleName();

    private MainActivityViewModel mViewModel;
    /**
     * Binding to main_activity.xlm
     */
    private MainActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setting the content view,
        binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_groups,
                R.id.navigation_search).build();
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build(); esto también sirve
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        /*        // Used by the default navigation component,
        // No longer required, pending to delete
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }*/


        mViewModel = new ViewModelProvider(this, new MainActivityViewModelFactory()).get(MainActivityViewModel.class);

        /* Observe the state of the User Auth */
        // Since it also receives the value when the observer is added, it also functions as
        // the first check to see if the user is logged in
        mViewModel.getUserState().observe(this, new Observer<UserAuthState>() {
            @Override
            public void onChanged(UserAuthState userState) {
                switch (userState) {
                    // user is signed out
                    case INVALID:
                    case SIGNED_OUT:
                    case NOT_LOGGED_IN:
                        Toast.makeText(MainActivity.this, R.string.result_logout_successful, Toast.LENGTH_SHORT).show();
                        login();
                        break;
                    // default
                    default:
                        break;
                }
            }
        });
    }

    /**
     * <p>Initiates the {@link LoginActivity} and finishes the {@link MainActivity}.</p>
     */
    private void login() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sign_out) {
            signOut();
        }
        return true;
    }

    /**
     * <p>Sign user out of the application.</p>
     */
    private void signOut() {
        mViewModel.signOut();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

}
