package com.andresd.socialverse.ui.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class DialogsFactory {

    private DialogsFactory() {
    }


    public static class TimeDialog extends DialogFragment {

        private final TimePickerDialog.OnTimeSetListener mListener;
        private LocalDateTime mLocalDateTime = null;

        public TimeDialog(TimePickerDialog.OnTimeSetListener onTimeSetListener) {
            this.mListener = onTimeSetListener;
        }

        public TimeDialog(TimePickerDialog.OnTimeSetListener onTimeSetListener, LocalDateTime localDateTime) {
            this.mListener = onTimeSetListener;
            this.mLocalDateTime = localDateTime;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            int hour;
            int minute;
            if (mLocalDateTime == null) {
                // Use the current date as the default time in the picker
                final Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);
            } else {
                hour = mLocalDateTime.getHour();
                minute = mLocalDateTime.getMinute();
            }

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(requireActivity(), mListener, hour, minute,
                    DateFormat.is24HourFormat(requireContext()));
        }
    }

    public static class DateDialog extends DialogFragment {

        private final DatePickerDialog.OnDateSetListener mListener;
        private LocalDateTime mLocalDatetime = null;

        public DateDialog(DatePickerDialog.OnDateSetListener onDateSetListener) {
            this.mListener = onDateSetListener;
        }

        public DateDialog(DatePickerDialog.OnDateSetListener onDateSetListener, LocalDateTime localDateTime) {
            this.mListener = onDateSetListener;
            this.mLocalDatetime = localDateTime;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            int year;
            int month;
            int day;
            if (mLocalDatetime == null) {
                // Use the current date as the default date in the picker
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
            } else {
                year = mLocalDatetime.getYear();
                month = mLocalDatetime.getMonthValue() - 1;
                day = mLocalDatetime.getDayOfMonth();
            }

            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), mListener, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(new Date().getTime());

            return datePickerDialog;

        }
    }
}
