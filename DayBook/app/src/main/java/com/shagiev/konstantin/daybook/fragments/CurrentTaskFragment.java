package com.shagiev.konstantin.daybook.fragments;


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
public class CurrentTaskFragment extends Fragment {

    private RecyclerView mRvCurrentTasks;
    private RecyclerView.LayoutManager mLayoutManager;
    private CurrentTaskAdapter mAdapter;


    public CurrentTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_current_task, container, false);
        mRvCurrentTasks = (RecyclerView) rootView.findViewById(R.id.rvCurrentTasks);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRvCurrentTasks.setLayoutManager(mLayoutManager);

        mAdapter = new CurrentTaskAdapter();
        mRvCurrentTasks.setAdapter(mAdapter);

        return rootView;
    }

    public void addTask(Task newTask){
        int position = -1;

        for(int i = 0; i < mAdapter.getItemCount(); i++){
            if(mAdapter.getItem(i).isTask()){
                Task task = (Task) mAdapter.getItem(i);
                if(newTask.getDate() < task.getDate()){
                     position = i;
                    break;
                }
            }
        }
        if(position != -1){
            mAdapter.addItem(position, newTask);
        } else{
            mAdapter.addItem(newTask);
        }
    }

}
