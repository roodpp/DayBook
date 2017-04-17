package com.shagiev.konstantin.daybook.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shagiev.konstantin.daybook.fragments.TasksFragment;
import com.shagiev.konstantin.daybook.model.Item;
import com.shagiev.konstantin.daybook.model.Separator;
import com.shagiev.konstantin.daybook.model.Task;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public abstract class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<Item> items = new ArrayList<>();
    protected TasksFragment mTasksFragment;

    public boolean containsSeparatorOverdue;
    public boolean containsSeparatorToday;
    public boolean containsSeparatorTomorrow;
    public boolean containsSeparatorFuture;


    public TaskAdapter(TasksFragment tasksFragment) {
        mTasksFragment = tasksFragment;
        this.items = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Item getItem(int position) {
        return items.get(position);
    }

    public void addItem(Item item) {
        items.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addItem(int position, Item item) {
        items.add(position, item);
        notifyItemChanged(position);
    }

    public void updateTask(Task newTask){
        for(int i = 0; i < getItemCount(); i++){
            if(getItem(i).isTask()){
                Task task = (Task) getItem(i);
                if(task.getTimeStamp() == newTask.getTimeStamp()){
                    removeItem(i);
                    getTasksFragment().addTask(newTask, false);
                }
            }
        }
    }

    public void removeItem(int position) {
        if (position >= 0 && position < getItemCount() - 1) {
            items.remove(position);
            notifyItemRemoved(position);
            if(position - 1 >= 0 && position<= getItemCount() -1){
                if(!getItem(position).isTask() && !getItem(position-1).isTask()){
                    Separator separator = (Separator) getItem(position-1);
                    checkSeparators(separator);
                    items.remove(position-1);
                    notifyItemRemoved(position-1);
                }
            } else if(getItemCount() - 1 >=0 && !getItem(getItemCount()-1).isTask()){
                Separator separator = (Separator) getItem(getItemCount()-1);
                checkSeparators(separator);

                int tempPosition = getItemCount() - 1;
                items.remove(tempPosition);
                notifyItemRemoved(tempPosition);
            }
        }

    }

    public void checkSeparators(Separator separator) {
        switch (separator.getType()){
         case Separator.TYPE_OVERDUE:
                containsSeparatorOverdue = false;
                break;
         case Separator.TYPE_TODAY:
                containsSeparatorOverdue = false;
                break;
         case Separator.TYPE_TOMORROW:
                containsSeparatorOverdue = false;
                break;
         case Separator.TYPE_FUTURE:
                containsSeparatorOverdue = false;
                break;
        }
    }

    public void removeAllItems(){
        if(getItemCount() != 0){
            items.clear();
            notifyDataSetChanged();
            containsSeparatorOverdue = false;
            containsSeparatorToday = false;
            containsSeparatorTomorrow = false;
            containsSeparatorFuture = false;
        }
    }

    protected class TaskViewHolder extends RecyclerView.ViewHolder {

        protected TextView title;
        protected TextView date;
        protected CircleImageView mCircleImageViewPriority;

        public TaskViewHolder(View itemView, TextView title, TextView date, CircleImageView priority) {
            super(itemView);
            this.title = title;
            this.date = date;
            this.mCircleImageViewPriority = priority;

        }
    }

    protected class SeparatorViewHolder extends RecyclerView.ViewHolder{

        protected TextView type;

        public SeparatorViewHolder(View itemView, TextView type) {
            super(itemView);
            this.type = type;
        }
    }


    public TasksFragment getTasksFragment() {
        return mTasksFragment;
    }
}
