package com.shagiev.konstantin.daybook.fragments.dialog;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.shagiev.konstantin.daybook.R;
import com.shagiev.konstantin.daybook.alarm.AlarmHelper;
import com.shagiev.konstantin.daybook.helper.Utils;
import com.shagiev.konstantin.daybook.model.Task;

import java.util.Calendar;
import java.util.Date;

public class EditTaskDialogFragment extends DialogFragment {


    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";

    private TextInputLayout mTilTitle;
    private EditText mEditTextTitle;
    private TextInputLayout mTilDate;
    private EditText mEditTextDate;
    private TextInputLayout mTilTime;
    private EditText mEditTextTime;
    private Calendar mCalendar;
    private Spinner mPrioritySpinner;
    private EditingTaskListener mEditingTaskListener;



    public static EditTaskDialogFragment newInstance(Task task) {
        Bundle args = new Bundle();
        args.putString("title", task.getTitle());
        args.putLong("date", task.getDate());
        args.putInt("priority", task.getPriority());
        args.putLong("timestamp", task.getTimeStamp());
        args.putInt("status", task.getStatus());

        EditTaskDialogFragment editTaskDialogFragment = new EditTaskDialogFragment();

        editTaskDialogFragment.setArguments(args);
        return editTaskDialogFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mEditingTaskListener = (EditingTaskListener) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implements EditingtaskListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle args = getArguments();

        String title = args.getString("title");
        long date = args.getLong("date");
        int priority = args.getInt("priority");
        long timestamp = args.getLong("timestamp");
        int status = args.getInt("status");

        final Task task = new Task(title, date, priority, status, timestamp);

        final AlertDialog.Builder  builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_edit_title);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View container = inflater.inflate(R.layout.dialog_task, null);
        builder.setView(container);

        mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.HOUR_OF_DAY, mCalendar.get(Calendar.HOUR_OF_DAY)+1);

        mTilTitle = (TextInputLayout) container.findViewById(R.id.tilDialogTaskTitle);
        mEditTextTitle = mTilTitle.getEditText();

        mTilDate = (TextInputLayout) container.findViewById(R.id.tilDialogTaskDate);
        mEditTextDate = mTilDate.getEditText();

        mTilTime = (TextInputLayout) container.findViewById(R.id.tilDialogTaskTime);
        mEditTextTime = mTilTime.getEditText();

        mEditTextTitle.setText(title);
        mEditTextTitle.setSelection(mEditTextTitle.length());
        if(date != 0){
            mEditTextDate.setText(Utils.getDate(new Date(date)));
            mEditTextTime.setText(Utils.getTime(new Date(date)));
        }

        mEditTextTitle.setHint(getResources().getString(R.string.task_title));
        mTilDate.setHint(getResources().getString(R.string.task_date));
        mTilTime.setHint(getResources().getString(R.string.task_time));

        mPrioritySpinner = (Spinner) container.findViewById(R.id.spDialogTaskPriority);
        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Task.PRIORITY_LEVELS);
        mPrioritySpinner.setAdapter(priorityAdapter);
        mPrioritySpinner.setSelection(priority);
        mPrioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                task.setPriority(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(mEditTextDate.length() != 0 || mEditTextTime.length() != 0){
            mCalendar.setTimeInMillis(date);
        }
        mEditTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                DialogFragment dialog = DatePickerFragment.newInstance(mCalendar.getTime());
                dialog.setTargetFragment(EditTaskDialogFragment.this, REQUEST_DATE);
                dialog.show(fragmentManager, DIALOG_DATE);
            }
        });

        mEditTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePickerFragment = new TimePickerFragment(){
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        mCalendar.set(Calendar.MINUTE, minute);
                        mCalendar.set(Calendar.SECOND, 0);
                        mEditTextTime.setText(Utils.getTime(mCalendar.getTime()));
                    }

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mEditTextTime.setText(null);
                    }
                };
                timePickerFragment.show(getFragmentManager(), DIALOG_TIME);
            }
        });

        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                task.setTitle(mEditTextTitle.getText().toString());
                task.setStatus(Task.STATUS_CURRENT);
                if(mEditTextDate.length() != 0 || mEditTextTime.length() != 0){
                    task.setDate(mCalendar.getTimeInMillis());
                    AlarmHelper.getInstance().setAlarm(task);
                }
                mEditingTaskListener.onTaskEdited(task);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                final Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                if(mEditTextTitle.length() == 0){
                    positiveButton.setEnabled(false);
                    mTilTitle.setError(getResources().getString(R.string.dialog_error_empty_title));
                }
                mEditTextTitle.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.length() == 0){
                            positiveButton.setEnabled(false);
                            mTilTitle.setError(getResources().getString(R.string.dialog_error_empty_title));
                        } else{
                            positiveButton.setEnabled(true);
                            mTilTitle.setErrorEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });
        return alertDialog;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK) {
            mEditTextDate.setText(null);
            return;
        }
        if(requestCode == REQUEST_DATE){
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCalendar.setTime(date);
            mEditTextDate.setText(Utils.getDate(date));
        }
    }


    public interface EditingTaskListener{
        void onTaskEdited(Task task);
    }

}
