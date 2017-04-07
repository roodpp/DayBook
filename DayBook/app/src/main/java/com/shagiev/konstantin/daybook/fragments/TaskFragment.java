package com.shagiev.konstantin.daybook.fragments;


import android.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.shagiev.konstantin.daybook.adapters.CurrentTaskAdapter;
import com.shagiev.konstantin.daybook.adapters.TaskAdapter;
import com.shagiev.konstantin.daybook.model.Task;

public abstract class TaskFragment  extends Fragment{
    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected TaskAdapter mAdapter;

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

    public abstract void moveTask(Task task);

}
