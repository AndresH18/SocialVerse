package com.andresd.socialverse.ui.main.home;

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

import com.andresd.socialverse.data.model.AbstractPost;
import com.andresd.socialverse.databinding.FragmentHomeBinding;
import com.andresd.socialverse.ui.adapters.PostRecyclerAdapter;
import com.andresd.socialverse.ui.main.MainActivityViewModel;
import com.andresd.socialverse.ui.main.MainActivityViewModelFactory;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getName();

    private FragmentHomeBinding binding;
    private PostRecyclerAdapter adapter;
    private MainActivityViewModel mViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: started");
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        Log.i(TAG, "onCreateView: finished");

        // set adapter
        adapter = new PostRecyclerAdapter();
        binding.list.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: started");
        // create viewModel
        mViewModel = new ViewModelProvider(requireActivity(), new MainActivityViewModelFactory()).get(MainActivityViewModel.class);

        mViewModel.getUniversityPosts().observe(getViewLifecycleOwner(), new Observer<ArrayList<AbstractPost>>() {
            @Override
            public void onChanged(ArrayList<AbstractPost> abstractPosts) {
                binding.nothingHere.setVisibility(abstractPosts == null || abstractPosts.size() == 0 ? View.VISIBLE : View.GONE);
                adapter.setData(abstractPosts);
            }
        });


        Log.i(TAG, "onViewCreated: finished");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: started");

        Log.i(TAG, "onCreate: finished");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: started");

        Log.i(TAG, "onStart: finished");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: started");

        Log.i(TAG, "onResume: finished");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: started");

        Log.i(TAG, "onPause: finsihed");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}