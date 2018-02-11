package com.shagiev.konstantin.daybook.adapters;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.shagiev.konstantin.daybook.fragments.CurrentTasksFragment;
import com.shagiev.konstantin.daybook.fragments.DoneTasksFragment;
import com.shagiev.konstantin.daybook.fragments.TasksFragment;

public class TabAdapter extends FragmentStatePagerAdapter {
    private int numberOfTabs;
    private CurrentTasksFragment currentTasksFragment;
    private DoneTasksFragment doneTasksFragment;

    public static final int CURRENT_TASK_FRAGMENT_POSITION = 0;
    public static final int DONE_TASK_FRAGMENT_POSITION = 1;

    public TabAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case CURRENT_TASK_FRAGMENT_POSITION:
                return new CurrentTasksFragment();
            case DONE_TASK_FRAGMENT_POSITION:
                return new DoneTasksFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
        switch (position) {
            case 0:
                currentTasksFragment = (CurrentTasksFragment) createdFragment;
                break;
            case 1:
                doneTasksFragment = (DoneTasksFragment) createdFragment;
                break;
        }
        return createdFragment;
    }

    public TasksFragment getFragment(int position){
        if(position == CURRENT_TASK_FRAGMENT_POSITION){
            return currentTasksFragment;
        } else{
            return doneTasksFragment;
        }
    }
}
