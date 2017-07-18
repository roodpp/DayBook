package com.shagiev.konstantin.daybook.fragments.dialog;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static final String EXTRA_DATE = "daybook.date";
    private static final String ARG_DATE = "date";
    Calendar mCalendar;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(getTargetFragment() != null) {
            Date date = (Date) getArguments().getSerializable(ARG_DATE);
            mCalendar = Calendar.getInstance();
            mCalendar.setTime(date);

            int year = mCalendar.get(Calendar.YEAR);
            int month = mCalendar.get(Calendar.MONTH);
            int day = mCalendar.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        } else{
            return super.onCreateDialog(savedInstanceState);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Date date;
        if(mCalendar!= null) {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            date = mCalendar.getTime();
        } else{
            date = new GregorianCalendar(year, month, dayOfMonth).getTime();
        }
        sendResult(Activity.RESULT_OK, date);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        sendResult(Activity.RESULT_CANCELED, new Date());
    }

    private void sendResult(int resultCode, Date date){
        Intent data = new Intent();
        data.putExtra(EXTRA_DATE, date);
        if(getTargetFragment() == null){
            getActivity().setResult(resultCode, data);
            getActivity().finish();
        }
        else {
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, data);
        }

    }

    public static DatePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setArguments(args);

        return datePickerFragment;
    }
}
