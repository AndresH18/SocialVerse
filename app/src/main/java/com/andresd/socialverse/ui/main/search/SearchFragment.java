package com.andresd.socialverse.ui.main.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.andresd.socialverse.databinding.SearchFragmentBinding;


public class SearchFragment extends Fragment {

    private SearchFragmentBinding binding;
    private SearchViewModel searchViewModel;

//    private RecyclerView recyclerView;
//    private RecyclerView.LayoutManager layoutManager;
//    private RecyclerView.Adapter adapter;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        binding = SearchFragmentBinding.inflate(inflater, container, false);

//        searchViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                binding.textSearch.setText(s);
//            }
//        });

//        layoutManager = new LinearLayoutManager(requireContext());
//        binding.recyclerView.setLayoutManager(layoutManager);
//
//        adapter = new GroupRecyclerAdapter();
//        binding.recyclerView.setAdapter(adapter);
//

        return binding.getRoot();

    }


}