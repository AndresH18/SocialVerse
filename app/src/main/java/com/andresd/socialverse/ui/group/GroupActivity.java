package com.andresd.socialverse.ui.group;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.andresd.socialverse.data.model.AbstractGroup;
import com.andresd.socialverse.data.model.AbstractUser;
import com.andresd.socialverse.databinding.ActivityGroupBinding;
import com.google.android.material.snackbar.Snackbar;

public class GroupActivity extends AppCompatActivity {

    private static final String TAG = GroupActivity.class.getSimpleName();

    private GroupViewModel mViewModel;

    private ActivityGroupBinding binding;
    private Menu menu;

    private AppBarConfiguration appBarConfiguration;
    private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: started");

        binding = ActivityGroupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_group);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // create/get viewModel
        mViewModel = new ViewModelProvider(this, new GroupViewModelFactory()).get(GroupViewModel.class);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO:
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mViewModel.getGroup().observe(this, new Observer<AbstractGroup>() {
            @Override
            public void onChanged(AbstractGroup abstractGroup) {
                customizeLayout();
            }
        });
        mViewModel.getUser().observe(this, new Observer<AbstractUser>() {
            @Override
            public void onChanged(AbstractUser abstractUser) {
                customizeLayout();
            }
        });


        Log.i(TAG, "onCreate: finished");
    }

    private void customizeLayout() {
        if (mViewModel.isUserSubscribed()) {
            // customize the layout for user belonging to group
//          menu == binding.toolbar.getMenu()
            Log.i(TAG, "onChanged: menus are equal");
            menu.findItem(R.id.menu_item_schedule).setVisible(true);
            binding.fab.setVisibility(View.VISIBLE);
//
        } else {

        }
    }

    private void getBundleArguments() {
        Log.i(TAG, "getBundleArguments: getting arguments from bundle");
//        try {
        String groupId = GroupActivityArgs.fromBundle(getIntent().getExtras()).getGroupId();
        String userid = GroupActivityArgs.fromBundle(getIntent().getExtras()).getUserId();
        mViewModel.setGroupId(groupId);
        mViewModel.setUser(userid);
//        } catch (Exception exception) {
//            Log.e(TAG, "onStart: Navigation Argument error", exception);
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: Started");
        // getting the arguments from the bundle and posting them on the viewModel
        getBundleArguments();
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
        getMenuInflater().inflate(R.menu.menu_group_options, menu);

        this.menu = menu;
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String message = "Options Item Message Holder";
        if (item.getItemId() == R.id.menu_item_refresh) {
            message = "Refresh";
        } else if (item.getItemId() == R.id.menu_item_sign_out) {
            message = "Sign Out";
        } else if (item.getItemId() == R.id.menu_item_settings) {
            message = "Settings";
        }
        Toast.makeText(GroupActivity.this, message, Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_group);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}