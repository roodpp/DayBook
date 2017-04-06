package com.shagiev.konstantin.daybook.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shagiev.konstantin.daybook.R;
import com.shagiev.konstantin.daybook.helper.Utils;
import com.shagiev.konstantin.daybook.model.Item;
import com.shagiev.konstantin.daybook.model.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CurrentTaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public static final int TYPE_TASK = 0;
    public static final int TYPE_SEPARATOR = 1;

    private List<Item> items = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case TYPE_TASK:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_task, parent, false);
                TextView title = (TextView) view.findViewById(R.id.tvTaskTitle);
                TextView date = (TextView) view.findViewById(R.id.tvTaskDate);

                return new TaskViewHolder(view, title, date);
            default: return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Item item = items.get(position);
        if(item.isTask()){
            holder.itemView.setEnabled(true);
            Task task = (Task) item;
            TaskViewHolder taskViewHolder = (TaskViewHolder) holder;

            taskViewHolder.title.setText(task.getTitle());
            if(task.getDate() != 0){
                taskViewHolder.date.setText(Utils.getFullDate(new Date(task.getDate())));
            }
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).isTask()) {
            return TYPE_TASK;
        } else {
            return TYPE_SEPARATOR;
        }
    }

    public Item getItem(int position) {
        return items.get(position);
    }

    public void addItem(Item item) {
        items.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addItem(int location, Item item) {
        items.add(location, item);
        notifyItemChanged(location);
    }


    private class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView date;

        public TaskViewHolder(View itemView, TextView title, TextView date) {
            super(itemView);
            this.title = title;
            this.date = date;

        }
    }
}
