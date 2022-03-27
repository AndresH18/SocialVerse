package com.andresd.socialverse.ui.main.groups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.andresd.socialverse.databinding.GroupsFragmentBinding;


public class GroupsFragment extends Fragment {

    private GroupsFragmentBinding binding;

    private GroupsViewModel groupsViewModel;


    public static GroupsFragment newInstance() {
        return new GroupsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        groupsViewModel = new ViewModelProvider(this).get(GroupsViewModel.class);
        binding = GroupsFragmentBinding.inflate(inflater, container, false);

        groupsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.textGroups.setText(s);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(GroupsViewModel.class);
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(GroupsViewModel.class);
//        // Use the ViewModel
//    }

}