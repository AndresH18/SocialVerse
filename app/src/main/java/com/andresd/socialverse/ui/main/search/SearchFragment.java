package com.andresd.socialverse.ui.main.search;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andresd.socialverse.data.model.AbstractGroup;
import com.andresd.socialverse.data.model.AbstractUser;
import com.andresd.socialverse.databinding.FragmentSearchBinding;
import com.andresd.socialverse.ui.adapters.GroupCardRecyclerAdapter;
import com.andresd.socialverse.ui.adapters.OnCardItemSelectedListener;
import com.andresd.socialverse.ui.main.MainActivityViewModel;
import com.andresd.socialverse.ui.main.MainActivityViewModelFactory;
import com.andresd.socialverse.ui.main.mygroups.MyGroupsFragmentDirections;

import java.util.List;


public class SearchFragment extends Fragment implements OnCardItemSelectedListener {
    private static final String TAG = SearchFragment.class.getSimpleName();

    private FragmentSearchBinding binding;
    private MainActivityViewModel mViewModel;

    private RecyclerView.LayoutManager layoutManager;
    //    private RecyclerView.Adapter<GroupsRecyclerAdapter.ViewHolder> adapter;
    private GroupCardRecyclerAdapter mAdapter;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: started");
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        Log.i(TAG, "onCreateView: finished");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: started");
        // create viewModel
        mViewModel = new ViewModelProvider(requireActivity(), new MainActivityViewModelFactory()).get(MainActivityViewModel.class);
//        searchViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                binding.textSearch.setText(s);
//            }
//        });


        /* Create Recycle View Element */
        // create layout manager for RecyclerView
        layoutManager = new LinearLayoutManager(requireContext());
        // set RecyclerView's layout manager
        binding.groupsRecyclerView.setLayoutManager(layoutManager);
        // create RecyclerView Adapter
        mAdapter = new GroupCardRecyclerAdapter(this);
//        adapter = new GroupsRecyclerAdapter();
        // set RecyclerView's adapter
        binding.groupsRecyclerView.setAdapter(mAdapter);
//        binding.groupsRecyclerView.setAdapter(adapter);


        mViewModel.getSearchGroups().observe(getViewLifecycleOwner(), new Observer<List<AbstractGroup>>() {
            @Override
            public void onChanged(List<AbstractGroup> groups) {
                mAdapter.setGroupCards(groups);
                binding.progressBar.setVisibility(View.GONE);
            }
        });


        /* Observe ViewModel Group changes FIXME */
//        mViewModel.getGroupLiveData().observe(getViewLifecycleOwner(), new Observer<Group>() {
//            @Override
//            public void onChanged(Group group) {
//                binding.progressBar.setVisibility(View.GONE);
//                if (group != null) {
////                    binding.groupCard.groupCardView.setVisibility(View.VISIBLE);
////                    binding.groupCard.itemTitle.setText(group.getName());
////                    binding.groupCard.itemDetail.setText(group.getDetail());
//
//                    /* Go to Group */
////                    SearchFragmentDirections.NavigateToGroupActivity directions = SearchFragmentDirections.navigateToGroupActivity(group.getName());
////                    Navigation.findNavController(requireView()).navigate(directions);
//                }
//            }
//        });

        /* Add action listener to EditText */
        binding.searchGroupEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    search();
                }
                return false;
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
        Log.i(TAG, "onDestroyView: started");

        Log.i(TAG, "onDestroyView: finished");
    }

    private void search() {
        final String searchString = binding.searchGroupEditText.getText().toString().trim().toLowerCase();
        if (!searchString.isEmpty()) {
            if (binding.tagsCheckBox.isChecked()) {
                // search by tags
                binding.progressBar.setVisibility(View.VISIBLE);
                mViewModel.searchGroupsByTags(searchString);
            } else {
                // search by name
                mViewModel.searchGroupByName(searchString);
            }

        }
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
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: started");
        binding = null;
        Log.i(TAG, "onDestroy: finished");
    }
}