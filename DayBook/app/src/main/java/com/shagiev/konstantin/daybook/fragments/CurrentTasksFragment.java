package com.shagiev.konstantin.daybook.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shagiev.konstantin.daybook.R;
import com.shagiev.konstantin.daybook.adapters.CurrentTaskAdapter;
import com.shagiev.konstantin.daybook.database.DBHelper;
import com.shagiev.konstantin.daybook.database.DBManager;
import com.shagiev.konstantin.daybook.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentTasksFragment extends TasksFragment {

    private OnDoneTaskListener mOnDoneTaskListener;
    public CurrentTasksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_current_tasks, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rvCurrentTasks);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CurrentTaskAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mOnDoneTaskListener = (OnDoneTaskListener) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement OnDoneTaskListener");
        }
    }

    @Override
    public void addTaskFromDB() {
        mAdapter.removeAllItems();
        List<Task> tasks = new ArrayList<>();
        tasks.addAll(mActivity.mDBHelper.getDBManager().getTasks(DBHelper.SELECTION_STATUS + " OR "
                + DBHelper.SELECTION_STATUS, new String[]{Integer.toString(Task.STATUS_CURRENT), Integer.toString(Task.STATUS_OVERDUE)},
        DBHelper.TASK_DATE_COLUMN));
        for(int i = 0; i < tasks.size(); i++){
            addTask(tasks.get(i), false);
        }
    }

    @Override
    public void moveTask(Task task) {
        mOnDoneTaskListener.onTaskDone(task);
    }

    @Override
    public void findTasks(String title) {
        mAdapter.removeAllItems();
        List<Task> tasks = new ArrayList<>();
        tasks.addAll(mActivity.mDBHelper.getDBManager().getTasks(DBHelper.SELECTION_LIKE_TITLE + " AND " + DBHelper.SELECTION_STATUS + " OR "
                        + DBHelper.SELECTION_STATUS, new String[]{"%" + title + "%",Integer.toString(Task.STATUS_CURRENT), Integer.toString(Task.STATUS_OVERDUE)},
                DBHelper.TASK_DATE_COLUMN));
        for(int i = 0; i < tasks.size(); i++){
            addTask(tasks.get(i), false);
        }
    }

    public interface OnDoneTaskListener{
        void onTaskDone(Task task);
    }
}
