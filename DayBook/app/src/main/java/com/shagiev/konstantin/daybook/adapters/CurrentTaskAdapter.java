package com.shagiev.konstantin.daybook.adapters;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.TextView;

import com.shagiev.konstantin.daybook.R;
import com.shagiev.konstantin.daybook.fragments.TaskFragment;
import com.shagiev.konstantin.daybook.helper.Utils;
import com.shagiev.konstantin.daybook.model.Item;
import com.shagiev.konstantin.daybook.model.Task;

import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class CurrentTaskAdapter extends TaskAdapter {


    public static final int TYPE_TASK = 0;
    public static final int TYPE_SEPARATOR = 1;

    public CurrentTaskAdapter(TaskFragment taskFragment) {
        super(taskFragment);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case TYPE_TASK:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_task, parent, false);
                TextView title = (TextView) view.findViewById(R.id.tvTaskTitle);
                TextView date = (TextView) view.findViewById(R.id.tvTaskDate);
                CircleImageView priority = (CircleImageView) view.findViewById(R.id.cvTaskPriority);

                return new TaskViewHolder(view, title, date, priority);
            default: return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Item item = items.get(position);
        if(item.isTask()){
            holder.itemView.setEnabled(true);
            final Task task = (Task) item;
            final TaskViewHolder taskViewHolder = (TaskViewHolder) holder;

            final View itemView = taskViewHolder.itemView;
            final Resources resources = itemView.getResources();

            taskViewHolder.title.setText(task.getTitle());
            if(task.getDate() != 0){
                taskViewHolder.date.setText(Utils.getFullDate(new Date(task.getDate())));
            } else{
                taskViewHolder.date.setText(null);
            }
            itemView.setVisibility(View.VISIBLE);

            itemView.setBackgroundColor(resources.getColor(R.color.grey_50));

            taskViewHolder.title.setTextColor(resources.getColor(R.color.primary_text_default_material_light));
            taskViewHolder.date.setTextColor(resources.getColor(R.color.secondary_text_default_material_light));
            taskViewHolder.mCircleImageViewPriority.setColorFilter(resources.getColor(task.getPriorityColor()));
            taskViewHolder.mCircleImageViewPriority.setImageResource(R.drawable.ic_checkbox_blank_circle_grey600_48dp);

            taskViewHolder.mCircleImageViewPriority.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    task.setStatus(Task.STATUS_DONE);
                    itemView.setBackgroundColor(resources.getColor(R.color.grey_200));

                    taskViewHolder.title.setTextColor(resources.getColor(R.color.primary_text_disabled_material_light));
                    taskViewHolder.date.setTextColor(resources.getColor(R.color.secondary_text_disabled_material_light));
                    taskViewHolder.mCircleImageViewPriority.setColorFilter(resources.getColor(task.getPriorityColor()));

                    ObjectAnimator flipIn = ObjectAnimator.ofFloat(taskViewHolder.mCircleImageViewPriority, "rotationY",-180f, 0f);
                    taskViewHolder.mCircleImageViewPriority.setImageResource(R.drawable.ic_checkbox_marked_circle_black_48dp);

                    flipIn.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if(task.getStatus() == Task.STATUS_DONE){

                                ObjectAnimator translationX = ObjectAnimator.ofFloat(itemView, "translationX", 0f, itemView.getWidth());

                                translationX.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        itemView.setVisibility(View.GONE);
                                        getTaskFragment().moveTask(task);
                                        removeItem(taskViewHolder.getLayoutPosition());
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                });

                                ObjectAnimator translationBackX = ObjectAnimator.ofFloat(itemView, "translationX", itemView.getWidth(), 0 );

                                AnimatorSet translationSet = new AnimatorSet();
                                translationSet.play(translationX).before(translationBackX);
                                translationSet.start();;

                                taskViewHolder.mCircleImageViewPriority.setColorFilter(resources.getColor(task.getPriorityColor()));
                            }

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });

                    flipIn.start();


                }
            });

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).isTask()) {
            return TYPE_TASK;
        } else {
            return TYPE_SEPARATOR;
        }
    }

}
