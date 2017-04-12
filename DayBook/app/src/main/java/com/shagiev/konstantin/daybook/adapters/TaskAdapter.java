package com.shagiev.konstantin.daybook.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shagiev.konstantin.daybook.fragments.TasksFragment;
import com.shagiev.konstantin.daybook.model.Item;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public abstract class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<Item> items = new ArrayList<>();
    protected TasksFragment mTasksFragment;


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

    public void removeItem(int position) {
        if (position >= 0 && position < getItemCount()) {
            items.remove(position);
            notifyItemRemoved(position);
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

    public TasksFragment getTasksFragment() {
        return mTasksFragment;
    }
}
