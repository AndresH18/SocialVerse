package com.andresd.socialverse.ui.group;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.andresd.socialverse.databinding.FragmentScheduleItemListBinding;
import com.andresd.socialverse.ui.group.placeholder.PlaceholderContent;

/**
 * A fragment representing a list of Items.
 */
public class ScheduleFragment extends Fragment {

    private static final String TAG = ScheduleFragment.class.getSimpleName();

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private FragmentScheduleItemListBinding binding;
    private GroupViewModel mViewModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ScheduleFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ScheduleFragment newInstance(int columnCount) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");

        binding = FragmentScheduleItemListBinding.inflate(inflater, container, false);
        if (mColumnCount <= 1) {
            binding.list.setLayoutManager(new LinearLayoutManager(requireContext()));
        } else {
            binding.list.setLayoutManager(new GridLayoutManager(requireContext(), mColumnCount));
        }
        // Set the adapter
        binding.list.setAdapter(new MyScheduleRecyclerViewAdapter(PlaceholderContent.ITEMS));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: ");

        mViewModel = new ViewModelProvider(requireActivity(), new GroupViewModelFactory()).get(GroupViewModel.class);
        mViewModel.setIsViewOnSchedule(true);

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
        mViewModel.setIsViewOnSchedule(false);
        binding = null;
    }
}