package com.shagiev.konstantin.daybook.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shagiev.konstantin.daybook.R;
import com.shagiev.konstantin.daybook.adapters.DoneTaskAdapter;
import com.shagiev.konstantin.daybook.alarm.AlarmHelper;
import com.shagiev.konstantin.daybook.database.DBHelper;
import com.shagiev.konstantin.daybook.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoneTasksFragment extends TasksFragment {

    private OnRestoreTaskListener mOnRestoreTaskListener;

    public DoneTasksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_done_tasks, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rvDoneTasks);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new DoneTaskAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void addTaskFromDB() {
        mAdapter.removeAllItems();
        List<Task> tasks = new ArrayList<>();
        tasks.addAll(mActivity.mDBHelper.getDBManager().getTasks(DBHelper.SELECTION_STATUS,
                new String[]{Integer.toString(Task.STATUS_DONE)},
                DBHelper.TASK_DATE_COLUMN));
        for(int i = 0; i < tasks.size(); i++){
            addTask(tasks.get(i), false);
        }
    }

    @Override
    public void moveTask(Task task) {
        if(task.getDate() != 0) {
            AlarmHelper.getInstance().setAlarm(task);
        }
        mOnRestoreTaskListener.onTaskRestore(task);
    }

    @Override
    public void findTasks(String title) {
        mAdapter.removeAllItems();
        List<Task> tasks = new ArrayList<>();
        tasks.addAll(mActivity.mDBHelper.getDBManager().getTasks(DBHelper.SELECTION_LIKE_TITLE + " AND " + DBHelper.SELECTION_STATUS
                , new String[]{"%" + title + "%",Integer.toString(Task.STATUS_DONE)},
                DBHelper.TASK_DATE_COLUMN));
        for(int i = 0; i < tasks.size(); i++){
            addTask(tasks.get(i), false);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mOnRestoreTaskListener = (OnRestoreTaskListener) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement OnRestoreTaskListener");
        }
    }
    public interface OnRestoreTaskListener{
        void onTaskRestore(Task task);
    }
}
