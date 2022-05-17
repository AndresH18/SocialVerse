package com.andresd.socialverse.ui.group;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.andresd.socialverse.R;
import com.andresd.socialverse.databinding.FragmentCreatePostBinding;
import com.google.android.material.snackbar.Snackbar;


public class CreatePostFragment extends Fragment {

    private FragmentCreatePostBinding binding;
    private GroupViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCreatePostBinding.inflate(inflater, container, false);

        binding.titleEditText.addTextChangedListener(titleTextWatcher);
        binding.messageEditTextMultiLine.addTextChangedListener(messageTextWatcher);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity(), new GroupViewModelFactory()).get(GroupViewModel.class);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title = binding.titleEditText.getText().toString();
                final String message = binding.messageEditTextMultiLine.getText().toString();
                if (formatIsValid(title, message)) {
                    // add post
                    Snackbar.make(v, "Adding post", Snackbar.LENGTH_SHORT).show();
                    mViewModel.createPost(title, message);
//                    requireActivity().onBackPressed();
                } else {
                    Snackbar.make(v, "Check the Information.", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean formatIsValid(@NonNull String title, @NonNull String message) {

        return !(title.trim().isEmpty() || message.trim().isEmpty() || message.length() < 10);
    }

    private TextWatcher titleTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // DO NOTHING
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // DO NOTHING
        }

        @Override
        public void afterTextChanged(Editable s) {
            final String title = binding.titleEditText.getText().toString();
            if (title.trim().isEmpty()) {
                binding.titleEditText.setError(getString(R.string.error_add_title));
            }
        }
    };
    private TextWatcher messageTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // DO NOTHING
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // DO NOTHING
        }

        @Override
        public void afterTextChanged(Editable s) {
            final String message = binding.messageEditTextMultiLine.getText().toString();
            if (message.trim().isEmpty() || message.length() < 10) {
                if (message.trim().isEmpty()) {
                    binding.messageEditTextMultiLine.setError(getString(R.string.error_add_message));
                } else if (message.length() < 10) {
                    binding.messageEditTextMultiLine.setError(getString(R.string.error_short_message));
                }
            }
        }
    };
}