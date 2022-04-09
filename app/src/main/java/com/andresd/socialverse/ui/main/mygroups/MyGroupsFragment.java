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

    private static final String TAG = MyGroupsFragment.class.getSimpleName();

    private FragmentMyGroupsBinding binding;
    private MainActivityViewModel mViewModel;

    private GroupCardRecyclerAdapter mAdapter;

    public static MyGroupsFragment newInstance() {
        return new MyGroupsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMyGroupsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
    }

    @Override
    public void onCardItemClicked(String groupId) {
        MyGroupsFragmentDirections.NavigateToGroupActivity directions = MyGroupsFragmentDirections.navigateToGroupActivity(groupId);
        AbstractUser user = mViewModel.getCurrentUser().getValue();

        if (user != null) {
            directions.setUserId(mViewModel.getCurrentUser().getValue().getId());
        }

        try {
            Navigation.findNavController(requireView()).navigate(directions);
        } catch (Exception exception) {
            Log.e(TAG, "onClick: Failed Navigation", exception);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

}