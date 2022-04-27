package com.andresd.socialverse.ui.main.mygroups;

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
import androidx.navigation.Navigation;

import com.andresd.socialverse.data.model.AbstractGroup;
import com.andresd.socialverse.data.model.AbstractUser;
import com.andresd.socialverse.data.model.GroupCard;
import com.andresd.socialverse.databinding.FragmentMyGroupsBinding;
import com.andresd.socialverse.ui.adapters.GroupCardRecyclerAdapter;
import com.andresd.socialverse.ui.adapters.OnCardItemSelectedListener;
import com.andresd.socialverse.ui.main.MainActivityViewModel;
import com.andresd.socialverse.ui.main.MainActivityViewModelFactory;
import com.google.firebase.firestore.DocumentReference;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class MyGroupsFragment extends Fragment implements OnCardItemSelectedListener {

    private static final String TAG = MyGroupsFragment.class.getName();

    private FragmentMyGroupsBinding binding;
    private MainActivityViewModel mViewModel;

    private GroupCardRecyclerAdapter mAdapter;

    public static MyGroupsFragment newInstance() {
        return new MyGroupsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: started");
        binding = FragmentMyGroupsBinding.inflate(inflater, container, false);
        Log.i(TAG, "onCreateView: finished");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: started");
        // create viewModel
        mViewModel = new ViewModelProvider(requireActivity(), new MainActivityViewModelFactory()).get(MainActivityViewModel.class);

        // create recycler view adapter
        mAdapter = new GroupCardRecyclerAdapter(this);
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
                        String name = String.valueOf(group.get("name"));
                        String detail = String.valueOf(group.get("detail"));

                        g.setId(id);
                        g.setName(name);
                        g.setDetail(detail);
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

        Log.i(TAG, "onViewCreated: finished");
    }

    @Override
    public void onCardItemClicked(String groupId) {
        AbstractUser user = mViewModel.getCurrentUser().getValue();
        String userId = user != null ? user.getId() : null;

        MyGroupsFragmentDirections.NavigateToGroupActivity directions =
                MyGroupsFragmentDirections.navigateToGroupActivity(groupId, userId);

        try {
            Navigation.findNavController(requireView()).navigate(directions);
        } catch (Exception exception) {
            Log.e(TAG, "onClick: Failed Navigation", exception);
        }
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
        Log.i(TAG, "onDestroyView: started");

        Log.i(TAG, "onDestroyView: finished");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: started");
        binding = null;
        Log.i(TAG, "onDestroy: finished");
    }

}