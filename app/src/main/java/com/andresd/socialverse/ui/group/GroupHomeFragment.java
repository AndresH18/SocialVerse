package com.andresd.socialverse.ui.group;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.andresd.socialverse.data.model.AbstractGroup;
import com.andresd.socialverse.databinding.FragmentGroupHomeBinding;

public class GroupHomeFragment extends Fragment {

    private static final String TAG = GroupHomeFragment.class.getSimpleName();

    private FragmentGroupHomeBinding binding;
    private GroupViewModel mViewModel;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentGroupHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState); // not required because super doesn't contain anything
        // create the viewModel
        mViewModel = new ViewModelProvider(requireActivity(), new GroupViewModelFactory()).get(GroupViewModel.class);

        mViewModel.getGroup().observe(getViewLifecycleOwner(), new Observer<AbstractGroup>() {
            @Override
            public void onChanged(AbstractGroup abstractGroup) {
                // TODO:
                //  layout: create layout file, maybe use book guide for a collapsable toolbar, etc.
                //  implement: show group information on layout.
            }
        });
        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}