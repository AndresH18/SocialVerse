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


public class ScheduleFragment extends Fragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private static final String TAG = ScheduleFragment.class.getSimpleName();

    private FragmentScheduleBinding binding;
    private GroupViewModel mViewModel;

    private DialogsFactory.DateDialog dateDialog;
    private DialogsFactory.TimeDialog timeDialog;


    private AbstractScheduleItem.MutableScheduleItem mItem;
    private String mTitle = "";
    private String mDetails = "";
    private int mYear = LocalDateTime.now().getYear();
    private int mMonth = LocalDateTime.now().getMonthValue();
    private int mDayOfMonth = LocalDateTime.now().getDayOfMonth();
    private int mHourOfDay = LocalDateTime.now().getHour();
    private int mMinute = LocalDateTime.now().getMinute();


    private TextWatcher textChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Do Nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Do Nothing
        }

        @Override
        public void afterTextChanged(Editable s) {
            mTitle = binding.titleEditText.getText().toString();
            mDetails = binding.detailsEditText.getText().toString();
        }
    };


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG, "onCreateView: inflating layout");
        binding = FragmentScheduleBinding.inflate(inflater, container, false);


        binding.titleEditText.addTextChangedListener(textChangeListener);
        binding.detailsEditText.addTextChangedListener(textChangeListener);

        binding.dateImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dateDialog == null) {
                    dateDialog = new DialogsFactory.DateDialog(ScheduleFragment.this);
                }
                dateDialog.show(requireActivity().getSupportFragmentManager(), "datePicker");
            }
        });

        binding.timeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timeDialog == null) {
                    timeDialog = new DialogsFactory.TimeDialog(ScheduleFragment.this);
                }
                timeDialog.show(requireActivity().getSupportFragmentManager(), "timePicker");
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
                if (mTitle.trim().isEmpty() || mDetails.trim().isEmpty()) {
                    if (mTitle.trim().isEmpty()) {
                        binding.titleEditText.setError(getString(R.string.invalid_title));
                    }
                    if (mDetails.trim().isEmpty()) {
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
        super.onViewCreated(view, savedInstanceState); // not required
        Log.i(TAG, "onViewCreated: acquiring view model");

        ScheduleFragmentArgs args = ScheduleFragmentArgs.fromBundle(getArguments());
        // FIXME : Do I need to be Fixed?
        mItem = (AbstractScheduleItem.MutableScheduleItem) args.getScheduleItem();

        mViewModel = new ViewModelProvider(requireActivity(), new GroupViewModelFactory()).get(GroupViewModel.class);

        if (mItem != null) {
            binding.titleEditText.setText(mItem.getTitle());

            binding.detailsEditText.setText(mItem.getDetails());

        } else {
            binding.date.setText(DateTimeFormatter.ofPattern("yyyy-MMMM-dd", Locale.getDefault()).
                    format(LocalDateTime.of(mYear,
                            mMonth,
                            mDayOfMonth,
                            mHourOfDay,
                            mMinute)));
            binding.time.setText(DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault()).
                    format(LocalDateTime.of(mYear,
                            mMonth,
                            mDayOfMonth,
                            mHourOfDay,
                            mMinute)));
        }


    }

    private void addSchedule(View v) {

//        Date d = new Date(mYear, mMonth, mDayOfMonth, mHourOfDay, mMinute);
        LocalDateTime now = LocalDateTime.of(mYear, mMonth, mDayOfMonth, mHourOfDay, mMinute);
        Date d = Date.from(now.atZone(ZoneId.systemDefault())
                .toInstant());

        // TODO: si custom parceable funciona de Navigation, devolver el item modificado....
        AbstractScheduleItem.MutableScheduleItem item = new AbstractScheduleItem.MutableScheduleItem(d, mTitle, mDetails);

        if (mViewModel.addSchedule(item)) {
            requireActivity().onBackPressed();
        } else {
            Snackbar.make(v, R.string.result_failed, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        setDate(year, month + 1, dayOfMonth);
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        setTime(hourOfDay, minute);
    }

    private void setTime(int hourOfDay, int minute) {
        mHourOfDay = hourOfDay;
        mMinute = minute;
        // String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
        String time = DateTimeFormatter.ofPattern("hh:mm a").format(LocalDateTime.of(mYear, mMonth, mDayOfMonth, mHourOfDay, mMinute));
        binding.time.setText(time);
    }

    private void setDate(int year, int month, int dayOfMonth) {
        mYear = year;
        mMonth = month;
        mDayOfMonth = dayOfMonth;
        String date = DateTimeFormatter.ofPattern("yyyy-MMMM-dd").format(LocalDateTime.of(mYear, mMonth, mDayOfMonth, mHourOfDay, mMinute));
        binding.date.setText(date);
    }

}