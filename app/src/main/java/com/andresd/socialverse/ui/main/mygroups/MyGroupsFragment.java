package com.andresd.socialverse.ui.main.mygroups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.andresd.socialverse.data.model.AbstractGroup;
import com.andresd.socialverse.data.model.AbstractUser;
import com.andresd.socialverse.data.model.GroupCard;
import com.andresd.socialverse.databinding.FragmentMyGroupsBinding;
import com.andresd.socialverse.ui.adapters.GroupCardRecyclerAdapter;
import com.andresd.socialverse.ui.main.MainActivityViewModel;
import com.andresd.socialverse.ui.main.MainActivityViewModelFactory;
import com.google.firebase.firestore.DocumentReference;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class MyGroupsFragment extends Fragment {

    private FragmentMyGroupsBinding binding;
    private MainActivityViewModel mViewModel;

    private GroupCardRecyclerAdapter mAdapter;

    public static MyGroupsFragment newInstance() {
        return new MyGroupsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(requireActivity(), new MainActivityViewModelFactory()).get(MainActivityViewModel.class);
        binding = FragmentMyGroupsBinding.inflate(inflater, container, false);

        // create recycler view adapter
        mAdapter = new GroupCardRecyclerAdapter();
        // set recycler view Adapter
        binding.groupsRecyclerView.setAdapter(mAdapter);

        mViewModel.getCurrentUser().observe(getViewLifecycleOwner(), new Observer<AbstractUser>() {
            @Override
            public void onChanged(AbstractUser abstractUser) {
                if (abstractUser != null) {
                    List<AbstractGroup> list = new LinkedList<>();
                    for (Map<String, Object> group : abstractUser.getGroups()) {
                        GroupCard g = new GroupCard();
                        DocumentReference id = (DocumentReference) group.get("id");
                        String
                        g.setId(group.get("id"));
                        g.setName(group.get("name"));
                        g.setDetail(group.get("detail"));
                        list.add(g);
                    }
                    mAdapter.setGroupCards(list);
                }
            }
        });

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