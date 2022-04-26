package com.andresd.socialverse.ui.group;

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
import com.andresd.socialverse.databinding.ActivityGroupBinding;

public class GroupActivity extends AppCompatActivity {

    private static final String TAG = GroupActivity.class.getSimpleName();

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
        mViewModel.cleanRepository();

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
        } else if (item.getItemId() == R.id.schedules) {
            message = "Schedules";
            Boolean b = mViewModel.getIsViewOnSchedule().getValue();
            if (b != null && !b) {
//            if (mViewModel.getViewOnSchedule() != null && !mViewModel.getViewOnSchedule().getValue()) {
                // FIXME : mViewModel.setIsViewOnSchedule(true);
                NavigationUI.onNavDestinationSelected(item, navController);
            }
        }
        Toast.makeText(GroupActivity.this, message, Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
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
