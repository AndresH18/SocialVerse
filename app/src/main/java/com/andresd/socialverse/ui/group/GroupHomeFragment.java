package com.andresd.socialverse.ui.group;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.andresd.socialverse.data.model.AbstractGroup;
import com.andresd.socialverse.databinding.FragmentGroupHomeBinding;
import com.google.android.material.snackbar.Snackbar;

public class GroupHomeFragment extends Fragment {

    private static final String TAG = GroupHomeFragment.class.getSimpleName();

    private FragmentGroupHomeBinding binding;
    private GroupViewModel mViewModel;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Log.i(TAG, "onCreateView: ");
        binding = FragmentGroupHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState); // not required because super doesn't contain anything
        Log.i(TAG, "onViewCreated: ");
        // create the viewModel
        mViewModel = new ViewModelProvider(requireActivity(), new GroupViewModelFactory()).get(GroupViewModel.class);

        mViewModel.getGroup().observe(getViewLifecycleOwner(), new Observer<AbstractGroup>() {
            @Override
            public void onChanged(AbstractGroup abstractGroup) {
                // TODO:
                //  layout: create layout file, maybe use book guide for a collapsable toolbar, etc.
                //  implement: show group information on the layout.
            }
        });

        mViewModel.getUserSubscriptionMediatorLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                /*if (aBoolean != null) {
                    binding.postFab.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
                } else {
                    binding.postFab.setVisibility(View.GONE);
                }*/
                binding.postFab.setVisibility(aBoolean != null ?
                        aBoolean ?
                                View.VISIBLE : View.GONE
                        : View.GONE);
            }
        });


        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        binding.postFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO:
                Snackbar.make(requireView(), "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: ");
        binding = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }
}