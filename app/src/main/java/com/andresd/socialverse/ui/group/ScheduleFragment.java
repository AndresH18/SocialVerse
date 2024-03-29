package com.andresd.socialverse.ui.group;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.andresd.socialverse.R;
import com.andresd.socialverse.data.model.AbstractScheduleItem;
import com.andresd.socialverse.databinding.FragmentScheduleBinding;
import com.andresd.socialverse.ui.dialogs.DialogsFactory;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class ScheduleFragment extends Fragment implements
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {

    private static final String TAG = ScheduleFragment.class.getName();

    private FragmentScheduleBinding binding;
    private GroupViewModel mViewModel;

    private DialogsFactory.DateDialog dateDialog;
    private DialogsFactory.TimeDialog timeDialog;

    private AbstractScheduleItem.MutableScheduleItem mScheduleItem;

    private LocalDateTime mLocalDateTime = LocalDateTime.now();

    private String mTitle = "";
    private String mDetails = "";
    private int mYear = mLocalDateTime.getYear();
    private int mMonth = mLocalDateTime.getMonthValue();
    private int mDayOfMonth = mLocalDateTime.getDayOfMonth();
    private int mHourOfDay = mLocalDateTime.getHour();
    private int mMinute = mLocalDateTime.getMinute();


    private final TextWatcher mTitleTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // DO Nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // DO NOTHING
        }

        @Override
        public void afterTextChanged(Editable s) {
            mTitle = binding.titleEditText.getText().toString();
        }
    };

    private final TextWatcher mDetailsTextWatcher = new TextWatcher() {
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
            mDetails = binding.detailsEditText.getText().toString();
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG, "onCreateView: inflating layout");
        binding = FragmentScheduleBinding.inflate(inflater, container, false);

        binding.titleEditText.addTextChangedListener(mTitleTextWatcher);
        binding.detailsEditText.addTextChangedListener(mDetailsTextWatcher);


        binding.dateImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dateDialog == null) {
                    dateDialog = new DialogsFactory.DateDialog(ScheduleFragment.this, mLocalDateTime);
                }
                dateDialog.show(requireActivity().getSupportFragmentManager(), "datePicker");
            }
        });

        binding.timeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timeDialog == null) {
                    timeDialog = new DialogsFactory.TimeDialog(ScheduleFragment.this, mLocalDateTime);
                }
                timeDialog.show(requireActivity().getSupportFragmentManager(), "timeDialog");
            }
        });

        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: onBackPressed");
                requireActivity().onBackPressed();
            }
        });

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTitle.isEmpty() || mDetails.isEmpty()) {
                    if (mTitle.isEmpty()) {
                        binding.titleEditText.setError(getString(R.string.invalid_title));
                    }
                    if (mDetails.isEmpty()) {
                        binding.detailsEditText.setError(getString(R.string.invalid_detail));
                    }
                } else {
                    addSchedule(v);
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i(TAG, "onViewCreated: retrieving arguments");
        ScheduleFragmentArgs args = ScheduleFragmentArgs.fromBundle(getArguments());

        Log.i(TAG, "onViewCreated: getting view Model");
        mViewModel = new ViewModelProvider(requireActivity(), new GroupViewModelFactory()).get(GroupViewModel.class);

        if (args.getItemIndex() >= 0) {
            // an item was selected
            mScheduleItem = (AbstractScheduleItem.MutableScheduleItem) mViewModel.getItem(args.getItemIndex());

            mLocalDateTime = mScheduleItem.getDateTime().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            mTitle = mScheduleItem.getTitle();
            mDetails = mScheduleItem.getDetails();
            mYear = mLocalDateTime.getYear();
            mMonth = mLocalDateTime.getMonthValue();
            mDayOfMonth = mLocalDateTime.getDayOfMonth();
            mHourOfDay = mLocalDateTime.getHour();
            mMinute = mLocalDateTime.getMinute();

            binding.titleEditText.setText(mTitle);
            binding.detailsEditText.setText(mDetails);
        }

        // else { /* no item selected -> new ScheduleItem */ }

        binding.date.setText(DateTimeFormatter.ofPattern("yyyy-MMMM-dd", Locale.getDefault())
                .format(mLocalDateTime));
        binding.time.setText(DateTimeFormatter.ofPattern("hh:mm a")
                .format(mLocalDateTime));
    }

    private void addSchedule(View v) {
        binding.progressBar.setVisibility(View.VISIBLE);

        /*
         Date d = new Date(mYear, mMonth, mDayOfMonth, mHourOfDay, mMinute);
        LocalDateTime now = LocalDateTime.of(mYear, mMonth, mDayOfMonth, mHourOfDay, mMinute);
        Date d = Date.from(now.atZone(ZoneId.systemDefault())
                .toInstant());

        AbstractScheduleItem.MutableScheduleItem item = new AbstractScheduleItem.MutableScheduleItem(d, mTitle, mDetails);

        if (mViewModel.addSchedule(item)) {
            requireActivity().onBackPressed();
        } else {
            Snackbar.make(v, R.string.result_failed, Snackbar.LENGTH_SHORT).show();
        }
         */
        Date date = Date.from(mLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());

        if (mScheduleItem == null) {
            AbstractScheduleItem.MutableScheduleItem item = new AbstractScheduleItem.MutableScheduleItem(date, mTitle, mDetails);
            if (mViewModel.addScheduleItem(item)) {
                Toast.makeText(requireContext(), R.string.result_saved, Toast.LENGTH_SHORT).show();
                requireActivity().onBackPressed();
            } else {
                Snackbar.make(v, R.string.result_failed, Snackbar.LENGTH_SHORT).show();
            }
        } else {
            mScheduleItem.setTimestampFromDate(date);
            mScheduleItem.setTitle(mTitle);
            mScheduleItem.setDetails(mDetails);

            if (mViewModel.updateElement(mScheduleItem)) {
//                Toast.makeText(requireContext(), R.string.result_update_successful, Toast.LENGTH_SHORT).show();
                requireActivity().onBackPressed();
            } else {
                Snackbar.make(v, R.string.result_update_failed, Snackbar.LENGTH_SHORT).show();
            }

        }

        binding.progressBar.setVisibility(View.GONE);

    }

    private void setDate(int year, int month, int dayOfMonth) {
        mYear = year;
        mMonth = month;
        mDayOfMonth = dayOfMonth;

        mLocalDateTime = LocalDateTime.of(mYear, mMonth, mDayOfMonth, mHourOfDay, mMinute);

        String date = DateTimeFormatter.ofPattern("yyyy-MMMM-dd", Locale.getDefault()).format(mLocalDateTime);
        binding.date.setText(date);
    }

    private void setTime(int hourOfDay, int minute) {
        mHourOfDay = hourOfDay;
        mMinute = minute;

        mLocalDateTime = LocalDateTime.of(mYear, mMonth, mDayOfMonth, mHourOfDay, mMinute);

        String time = DateTimeFormatter.ofPattern("hh:mm a").format(mLocalDateTime);
        binding.time.setText(time);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        setDate(year, month + 1, dayOfMonth);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        setTime(hourOfDay, minute);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
