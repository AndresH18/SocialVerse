package com.andresd.socialverse;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.andresd.socialverse.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        // FIXME: Change for navigation Component
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }


}