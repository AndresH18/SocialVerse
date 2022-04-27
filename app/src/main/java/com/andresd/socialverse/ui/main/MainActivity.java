package com.andresd.socialverse.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.andresd.socialverse.R;
import com.andresd.socialverse.data.repository.UserRepository;
import com.andresd.socialverse.databinding.ActivityMainBinding;
import com.andresd.socialverse.ui.login.LoginActivity;
import com.andresd.socialverse.ui.main.mygroups.MyGroupsFragment;
import com.google.android.material.snackbar.Snackbar;

/**
 * <b>Main Activity</b>
 * <p>This Class is the Controller for the main activity.<br>
 * It manages the interaction between the {@link com.andresd.socialverse.ui.main.home.HomeFragment},
 * {@link com.andresd.socialverse.ui.main.search.SearchFragment} and {@link MyGroupsFragment};
 * as well as the main {@link androidx.appcompat.widget.Toolbar} and
 * {@link com.google.android.material.bottomnavigation.BottomNavigationView}.</p>
 */

public class MainActivity extends AppCompatActivity {
    /**
     * Logcat tag
     */
    private static final String TAG = MainActivity.class.getName();

    private MainActivityViewModel mViewModel;
    /**
     * Binding to main_activity.xlm
     */
    private ActivityMainBinding binding;
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: started");

        // setting the content view,
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_my_groups,
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

        // create/get viewModel
        mViewModel = new ViewModelProvider(this, new MainActivityViewModelFactory()).get(MainActivityViewModel.class);

        /* Observe the state of the User Auth */
        // Since it also receives the value when the observer is added, it also functions as
        // the first check to see if the user is logged in
        mViewModel.getUserState().observe(this, new Observer<UserRepository.UserAuthState>() {
            @Override
            public void onChanged(UserRepository.UserAuthState userState) {
                switch (userState) {
                    // user is signed out
                    case INVALID:
                    case SIGNED_OUT:
                        Toast.makeText(MainActivity.this, R.string.result_logout_successful, Toast.LENGTH_SHORT).show();
                    case NOT_LOGGED_IN:
                        goToLogin();
                        break;
                    // default
                    default:
                        break;
                }
            }
        });

//        binding.navView.setOnItemSelectedListener(this);
        Log.i(TAG, "onCreate: finished");
    }

    /**
     * <p>Initiates the {@link LoginActivity} and finishes the {@link MainActivity}.</p>
     */
    private void goToLogin() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    /**
     * <p>Sign user out of the application.</p>
     */
    private void signOut() {
        mViewModel.signOut();
    }

    /**
     * <p>Called when the bottom navigation items are selected</p>
     */
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Sets the add icon's visibility when the item is search
        // TODO: decidir si mejor poner un button cuando no haya resultado sobre el grupo.
        //  Si se va a poner el button, entonces borrar el item de agregar grupo del menu
        // FIXME: Se va a tener que crear el button, porque usar este listener daña la funcionalidad del nav_host
        binding.toolbar.getMenu().getItem(0).setVisible(item.getItemId() == R.id.navigation_search);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_item_sign_out) {
            Snackbar.make(binding.coordinator, R.string.question_sign_out, Snackbar.LENGTH_LONG)
                    .setAction(R.string.action_sign_out, view -> signOut()).show();
        }
        // TODO (TESTING)
        if (item.getItemId() == R.id.menu_item_refresh) {
//            Snackbar.make(binding.coordinator, "Restarting Activity", Snackbar.LENGTH_LONG)
//                    .setAction("Restart?", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            startActivity(new Intent(MainActivity.this, MainActivity.class));
//                            finish();
//                        }
//                    }).show();
            Toast.makeText(this, "Restarting Activity", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: started");

        Log.i(TAG, "onStart: finished");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: started");

        Log.i(TAG, "onRestart: finished");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: started");

        Log.i(TAG, "onResume: finished");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // called after onResume
        Log.i(TAG, "onCreateOptionsMenu: started");
        getMenuInflater().inflate(R.menu.menu_main_options, menu);
        Log.i(TAG, "onCreateOptionsMenu: finished");
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: started");

        Log.i(TAG, "onPause: finished");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: started");

        Log.i(TAG, "onStop: finished");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: started");

        Log.i(TAG, "onDestroy: finished");
    }



    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
