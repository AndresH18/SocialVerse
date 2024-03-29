package com.andresd.socialverse.ui.group;

import android.content.Intent;
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
import com.andresd.socialverse.data.repository.UserRepository;
import com.andresd.socialverse.databinding.ActivityGroupBinding;
import com.google.android.material.snackbar.Snackbar;

public class GroupActivity extends AppCompatActivity {

    private static final String TAG = GroupActivity.class.getName();

    private GroupViewModel mViewModel;

    public ActivityGroupBinding binding;
    private Menu menu;

    private AppBarConfiguration appBarConfiguration;
    private NavController navController;


    private Observer<Boolean> subscriptionObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean v) {
            if (v != null) {
                menu.findItem(R.id.schedules).setVisible(v);
//                binding.postFab.setVisibility(v ? View.VISIBLE : View.GONE);
            }
        }

    };

    private Observer<Boolean> scheduleViewVisibilityObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean aBoolean) {
            /*if (aBoolean != null) {
                if (mViewModel.isUserSubscribed()) {
                    if (aBoolean) {
                        //  mViewModel.setIsViewOnSchedule(true);
                        //
                        menu.findItem(R.id.schedules).setVisible(false);
//                        binding.postFab.setVisibility(View.GONE);
                        //

                    } else {
                        // mViewModel.setIsViewOnSchedule(false);
                        //
                        menu.findItem(R.id.schedules).setVisible(true);
//                        binding.postFab.setVisibility(View.VISIBLE);
                        //

                    }
                } else {
                    menu.findItem(R.id.schedules).setVisible(true);
//                    binding.postFab.setVisibility(View.VISIBLE);
                }
            }*/
            if (aBoolean != null) {
                if (mViewModel.isUserSubscribed()) {
                    menu.findItem(R.id.schedules).setVisible(!aBoolean);
                } else {
                    menu.findItem(R.id.schedules).setVisible(true);
//                    binding.postFab.setVisibility(View.VISIBLE);
                }
            }
        }
    };


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

        mViewModel.getUserState().observe(this, new Observer<UserRepository.UserAuthState>() {
            @Override
            public void onChanged(UserRepository.UserAuthState userAuthState) {
                switch (userAuthState) {
                    // user is signed out
                    case INVALID:
                    case SIGNED_OUT:
                        Toast.makeText(GroupActivity.this, R.string.result_logout_successful, Toast.LENGTH_SHORT).show();
                    case NOT_LOGGED_IN:
                        // go back
                        finish();
                        break;
                    default:
                        break;
                }
            }
        });


//        mViewModel.getGroup().observe
//        mViewModel.getUser().observe


        Log.i(TAG, "onCreate: finished");
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

        // setting the subscription observer
        mViewModel.getUserSubscriptionMediatorLiveData().observe(this, subscriptionObserver);
        mViewModel.getIsViewOnSchedule().observe(this, scheduleViewVisibilityObserver);


        Log.i(TAG, "onCreateOptionsMenu: finished");
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: started");

        mViewModel.getUserSubscriptionMediatorLiveData().removeObserver(subscriptionObserver);
        mViewModel.getIsViewOnSchedule().removeObserver(scheduleViewVisibilityObserver);


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
        if (mViewModel != null) {
            mViewModel.cleanRepository();
        }
        Log.i(TAG, "onDestroy: finished");
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_item_refresh) {
            Snackbar.make(binding.coordinator, R.string.question_reload, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.action_reload, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = getIntent();
                            startActivity(intent);
                            finish();
                        }
                    }).show();

        } else if (item.getItemId() == R.id.menu_item_sign_out) {
            Snackbar.make(binding.coordinator, R.string.question_sign_out, Snackbar.LENGTH_LONG)
                    .setAction(R.string.action_sign_out, view -> signOut()).show();
        } else if (item.getItemId() == R.id.menu_item_settings) {
            Snackbar.make(binding.coordinator, "Settings not implemented", Snackbar.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.schedules) {

            Boolean b = mViewModel.getIsViewOnSchedule().getValue();
            if (b != null && !b) {
//            if (mViewModel.getViewOnSchedule() != null && !mViewModel.getViewOnSchedule().getValue()) {
                // FIXME : mViewModel.setIsViewOnSchedule(true);
                NavigationUI.onNavDestinationSelected(item, navController);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        mViewModel.signOut();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Boolean b = mViewModel.getIsViewOnSchedule().getValue();
//        if (isViewOnSchedule)
        if (b != null && b)
            mViewModel.setIsViewOnSchedule(false);
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_group);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


}
