package com.andresd.socialverse.ui.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

public class DialogsFactory {

    private DialogsFactory() {
    }


    public static class TimeDialog extends DialogFragment {

        private final TimePickerDialog.OnTimeSetListener mListener;

        public TimeDialog(TimePickerDialog.OnTimeSetListener onTimeSetListener) {
            this.mListener = onTimeSetListener;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(requireActivity(), mListener, hour, minute,
                    DateFormat.is24HourFormat(requireContext()));
        }
    }

    public static class DateDialog extends DialogFragment {

        private final DatePickerDialog.OnDateSetListener mListener;

        public DateDialog(DatePickerDialog.OnDateSetListener onDateSetListener) {
            this.mListener = onDateSetListener;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), mListener, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(new Date().getTime());

            return datePickerDialog;

        }
    }
}
