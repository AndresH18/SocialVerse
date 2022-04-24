package com.andresd.socialverse.ui.group;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.andresd.socialverse.data.model.AbstractScheduleItem;
import com.andresd.socialverse.databinding.FragmentMySchedulesListBinding;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class MySchedulesFragment extends Fragment implements MyScheduleRecyclerViewAdapter.OnItemListener {

    private static final String TAG = MySchedulesFragment.class.getSimpleName();

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private MyScheduleRecyclerViewAdapter adapter;

    private FragmentMySchedulesListBinding binding;
    private GroupViewModel mViewModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MySchedulesFragment() {
    }

    // TODO: Customize parameter initialization
    public static MySchedulesFragment newInstance(int columnCount) {
        MySchedulesFragment fragment = new MySchedulesFragment();
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

        binding = FragmentMySchedulesListBinding.inflate(inflater, container, false);
        if (mColumnCount <= 1) {
            binding.list.setLayoutManager(new LinearLayoutManager(requireContext()));
        } else {
            binding.list.setLayoutManager(new GridLayoutManager(requireContext(), mColumnCount));
        }
        // Set the adapter
        adapter = new MyScheduleRecyclerViewAdapter(this);
        binding.list.setAdapter(adapter);

        /* Listeners */

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySchedulesFragmentDirections.actionSchedulesToAddScheduleFragment();
                Navigation.findNavController(v).navigate(MySchedulesFragmentDirections.actionSchedulesToAddScheduleFragment());
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: ");

        mViewModel = new ViewModelProvider(requireActivity(), new GroupViewModelFactory()).get(GroupViewModel.class);
        mViewModel.setIsViewOnSchedule(true);
        mViewModel.getScheduleItemsList().observe(getViewLifecycleOwner(), new Observer<ArrayList<AbstractScheduleItem>>() {
            @Override
            public void onChanged(ArrayList<AbstractScheduleItem> abstractScheduleItems) {
                adapter.updateDataSet(abstractScheduleItems);
                // Usar conjuntos y la diferencia entre ellos para saber que elementos son nuevos
            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
        mViewModel.setIsViewOnSchedule(false);
        binding = null;
    }

    @Override
    public void onDeleteItem() {
        // TODO
    }

    @Override
    public void onModifyItem() {
        // TODO
    }
}