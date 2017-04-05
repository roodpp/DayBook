package com.shagiev.konstantin.daybook.fragments.dialog;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static final String EXTRA_DATE = "daybook.date";
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Date date = new GregorianCalendar(year, month, dayOfMonth).getTime();
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
}
