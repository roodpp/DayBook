package com.shagiev.konstantin.daybook.adapters;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.shagiev.konstantin.daybook.fragments.CurrentTasksFragment;
import com.shagiev.konstantin.daybook.fragments.DoneTasksFragment;

public class TabAdapter extends FragmentStatePagerAdapter {

    private int mNumberOfTabs;

    public static final int CURRENT_TASK_FRAGMENT_POSITION = 0;
    public static final int DONE_TASK_FRAGMENT_POSITION = 1;

    private CurrentTasksFragment mCurrentTaskFragment;
    private DoneTasksFragment mDoneTaskFragment;

    public TabAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        mNumberOfTabs = numberOfTabs;
        mCurrentTaskFragment = new CurrentTasksFragment();
        mDoneTaskFragment = new DoneTasksFragment();
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case CURRENT_TASK_FRAGMENT_POSITION:
                return mCurrentTaskFragment;
            case DONE_TASK_FRAGMENT_POSITION:
                return mDoneTaskFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumberOfTabs;
    }
}
