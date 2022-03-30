package com.andresd.socialverse.ui.main.mygroups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.andresd.socialverse.databinding.FragmentMyGroupsBinding;


public class MyGroupsFragment extends Fragment {

    private FragmentMyGroupsBinding binding;

    private MyGroupsViewModel groupsViewModel;

    public static MyGroupsFragment newInstance() {
        return new MyGroupsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        groupsViewModel = new ViewModelProvider(this).get(MyGroupsViewModel.class);
        binding = FragmentMyGroupsBinding.inflate(inflater, container, false);
        groupsViewModel.hello = "HAS VALUE";

//        groupsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                binding.textGroups.setText(s);
//            }
//        });

        return binding.getRoot();
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(GroupsViewModel.class);
//        // Use the ViewModel
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

}