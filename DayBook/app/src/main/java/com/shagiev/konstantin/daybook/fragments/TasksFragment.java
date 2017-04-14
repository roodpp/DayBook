package com.shagiev.konstantin.daybook.fragments;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shagiev.konstantin.daybook.R;
import com.shagiev.konstantin.daybook.activities.MainActivity;
import com.shagiev.konstantin.daybook.adapters.CurrentTaskAdapter;
import com.shagiev.konstantin.daybook.adapters.TaskAdapter;
import com.shagiev.konstantin.daybook.model.Item;
import com.shagiev.konstantin.daybook.model.Task;

public abstract class TasksFragment extends Fragment{
    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected TaskAdapter mAdapter;

    public MainActivity mActivity;


    public void addTask(Task newTask, boolean saveToDB){
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
        if(saveToDB){
            mActivity.mDBHelper.saveTask(newTask);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getActivity() != null){
            mActivity = (MainActivity) getActivity();
        }
        addTaskFromDB();
    }

    public abstract void addTaskFromDB();

    public abstract void moveTask(Task task);

    public abstract void findTasks(String title);

    public void removeTaskDialog(final int location){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        dialogBuilder.setMessage(R.string.dialog_removing_message);

        Item item = mAdapter.getItem(location);
        if (item.isTask()){
            Task task = (Task) item;
            final long timestamp = task.getTimeStamp();
            final boolean[] isRemoved = {false};

            dialogBuilder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    mAdapter.removeItem(location);
                    isRemoved[0] = true;

                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.coordinator), R.string.removed, Snackbar.LENGTH_LONG);
                    snackbar.setAction(R.string.dialog_cancel, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                addTask(mActivity.mDBHelper.getDBManager().getTask(timestamp),false);
                                isRemoved[0] = false;
                        }
                    });

                    snackbar.getView().addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                        @Override
                        public void onViewAttachedToWindow(View v) {

                        }

                        @Override
                        public void onViewDetachedFromWindow(View v) {
                            if(isRemoved[0]){
                                mActivity.mDBHelper.removeTask(timestamp);
                            }
                        }
                    });

                    snackbar.show();

                    dialog.dismiss();
                }
            });

            dialogBuilder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }

            });

            dialogBuilder.show();

        }
    }

}
