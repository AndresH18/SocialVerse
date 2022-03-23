package com.andresd.socialverse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.andresd.socialverse.databinding.MainActivityBinding;
import com.andresd.socialverse.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

/**
 * <b>Main Activity</b>
 * <p>This Class is the Controller for the main activity.<br>
 *     It manages the interaction between the {@link com.andresd.socialverse.ui.main.home.HomeFragment},
 *     {@link com.andresd.socialverse.ui.main.search.SearchFragment} and {@link com.andresd.socialverse.ui.main.groups.GroupsFragment};
 *     as well as the main {@link androidx.appcompat.widget.Toolbar} and
 *     {@link com.google.android.material.bottomnavigation.BottomNavigationView}.</p>
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Logcat tag
     */
    private static final String TAG = MainActivity.class.getSimpleName();
    /**
     * FirebaseAuth Reference
     */
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
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

        // check if the user is logged in
        if (mAuth.getCurrentUser() == null) {
            Log.d(TAG, "onCreate: User is not logged, starting LoginActivity");
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }

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

/* TODO: Create sign out listener

        mAuth.getUser().observe(this, new Observer<LoggedInUser>() {
            @Override
            public void onChanged(LoggedInUser loggedInUser) {
                if (loggedInUser == null) {
                    // TODO: sign out from activity
                    Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_LONG).show();
                }
            }
        });*/


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sign_out) {
            signOut();
        }
        return true;
    }

    private void signOut() {
        mAuth.signOut();
        Toast.makeText(this, R.string.result_logout_successful, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

}
