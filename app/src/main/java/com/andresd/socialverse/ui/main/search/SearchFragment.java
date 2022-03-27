package com.andresd.socialverse.ui.main.search;

import android.os.Bundle;
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
import androidx.recyclerview.widget.RecyclerView;

import com.andresd.socialverse.data.model.Group;
import com.andresd.socialverse.databinding.FragmentSearchBinding;


public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private SearchViewModel mViewModel;

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        binding = FragmentSearchBinding.inflate(inflater, container, false);

//        searchViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                binding.textSearch.setText(s);
//            }
//        });

        /* Create Recycle View Element */
        // create layout manager for RecyclerView
//        layoutManager = new LinearLayoutManager(requireContext());
//        // set RecyclerView's layout manager
//        binding.recyclerView.setLayoutManager(layoutManager);
//        // create RecyclerView Adapter
//        adapter = new GroupRecyclerAdapter();
//        // set RecyclerView's adapter
//        binding.recyclerView.setAdapter(adapter);

        /* Observe ViewModel Group changes */
        mViewModel.getGroupLiveData().observe(getViewLifecycleOwner(), new Observer<Group>() {
            @Override
            public void onChanged(Group group) {
                binding.progressBar.setVisibility(View.GONE);
                if (group != null) {
                    binding.groupCard.groupCardView.setVisibility(View.VISIBLE);
                    binding.groupCard.itemTitle.setText(group.getName());
                    binding.groupCard.itemDetail.setText(group.getDetail());
                } else {
                    binding.groupCard.groupCardView.setVisibility(View.INVISIBLE);
                    binding.groupCard.itemTitle.setText(null);
                    binding.groupCard.itemDetail.setText(null);
                }
            }
        });
        /* Add Click listener to Button */
        binding.searchButton.setOnClickListener(v -> {
//            ((GroupRecyclerAdapter) adapter).add(
//                    new String[]{"Peliculas", "Teatro", "Videojuegos"},
//                    new String[]{"cine", "como los papas de batman", "Yaya, juegos"});
            searchGroup();
        });
        /* Add action listener to EditText */
        binding.searchGroupEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    searchGroup();
                }
                return false;
            }
        });

        return binding.getRoot();
    }

    private void searchGroup() {
        final String searchString = binding.searchGroupEditText.getText().toString().trim().toLowerCase();
        if (!searchString.isEmpty()) {
            binding.progressBar.setVisibility(View.VISIBLE);
            mViewModel.searchGroup(searchString);
        }
    }


}