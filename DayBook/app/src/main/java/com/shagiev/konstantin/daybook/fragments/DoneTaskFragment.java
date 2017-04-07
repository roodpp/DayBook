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
import com.shagiev.konstantin.daybook.model.Task;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoneTaskFragment extends TaskFragment {

    private OnRestoreTaskListener mOnRestoreTaskListener;

    public DoneTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_done_task, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rvDoneTasks);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new DoneTaskAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void moveTask(Task task) {
        mOnRestoreTaskListener.onTaskRestore(task);
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
