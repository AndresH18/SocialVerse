package com.andresd.socialverse.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.andresd.socialverse.data.model.LoginRepository;
import com.andresd.socialverse.databinding.MainFragmentBinding;
import com.andresd.socialverse.ui.login.LoginActivity;

public class MainFragment extends Fragment {
    private static final String TAG = MainFragment.class.getSimpleName();

    private MainFragmentBinding binding;

    private MainViewModel mViewModel;
    private LoginRepository mAuth = LoginRepository.getInstance();

    public static MainFragment newInstance() {
        return new MainFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = MainFragmentBinding.inflate(inflater, container, false);

        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
        if (getActivity() != null) {
            if (mAuth.getCurrentUser() == null) {
                Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(loginIntent);
                getActivity().finish();
            }
        } else {
            Log.w(TAG, "Fragment is not attached");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}