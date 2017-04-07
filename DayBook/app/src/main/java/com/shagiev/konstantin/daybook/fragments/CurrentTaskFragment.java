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
import com.shagiev.konstantin.daybook.adapters.CurrentTaskAdapter;
import com.shagiev.konstantin.daybook.model.Task;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentTaskFragment extends TaskFragment {

    private OnDoneTaskListener mOnDoneTaskListener;
    public CurrentTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_current_task, container, false);
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
    public void moveTask(Task task) {
        mOnDoneTaskListener.onTaskDone(task);
    }

    public interface OnDoneTaskListener{
        void onTaskDone(Task task);
    }
}
