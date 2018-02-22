package com.shagiev.konstantin.daybook.fragments.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public static final String EXTRA_TIME = "daybook.time";
    private static final String ARG_TIME = "time";
    Calendar mCalendar;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(getTargetFragment() != null) {
            Date date = (Date) getArguments().getSerializable(ARG_TIME);
            mCalendar = Calendar.getInstance();
            mCalendar.setTime(date);

            int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
            int minute = mCalendar.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        } else{
            return super.onCreateDialog(savedInstanceState);
        }
    }


    @Override
    public void onCancel(DialogInterface dialog) {
        sendResult(Activity.RESULT_CANCELED, new Date());
    }

    private void sendResult(int resultCode, Date date){
        Intent data = new Intent();
        data.putExtra(EXTRA_TIME, date);
        if(getTargetFragment() == null){
            getActivity().setResult(resultCode, data);
            getActivity().finish();
        }
        else {
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, data);
        }

    }

    public static TimePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME, date);
        TimePickerFragment datePickerFragment = new TimePickerFragment();
        datePickerFragment.setArguments(args);
        return datePickerFragment;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Date date;
        mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mCalendar.set(Calendar.MINUTE, minute);
        mCalendar.set(Calendar.SECOND, 0);
        date = mCalendar.getTime();
        sendResult(Activity.RESULT_OK, date);
    }


}
