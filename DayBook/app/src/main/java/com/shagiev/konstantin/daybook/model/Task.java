package com.shagiev.konstantin.daybook.model;


import com.shagiev.konstantin.daybook.R;

import java.util.Date;

public class Task implements Item {


    public static final int PRIORITY_LOW = 0;
    public static final int PRIORITY_NORMAL = 1;
    public static final int PRIORITY_HIGH = 2;

    public static final String [] PRIORITY_LEVELS = {"Low Priority", "Normal Priority", "High Priority"};

    public static final int STATUS_OVERDUE = 0;
    public static final int STATUS_CURRENT = 1;
    public static final int STATUS_DONE = 2;

    private String mTitle;
    private long mDate;
    private int mPriority;
    private int mStatus;
    private long mTimeStamp;
    private int dateStatus;

    public Task() {
        mStatus = -1;
        mTimeStamp = new Date().getTime();
    }

    public Task(String title, long date, int priority, int status, long timeStamp) {
        mTitle = title;
        mDate = date;
        mPriority = priority;
        mStatus = status;
        mTimeStamp = timeStamp;
    }

    public Task(String title, long date) {
        mTitle = title;
        mDate = date;
    }

    @Override
    public boolean isTask() {
        return true;
    }

    public int getPriorityColor(){
        switch (getPriority()){
            case PRIORITY_LOW:
                if(getStatus() == STATUS_CURRENT || getStatus() == STATUS_OVERDUE){
                    return R.color.priority_low;
                } else{
                    return R.color.priority_low_done;
                }
            case PRIORITY_NORMAL:
                if(getStatus() == STATUS_CURRENT || getStatus() == STATUS_OVERDUE){
                    return R.color.priority_normal;
                } else{
                    return R.color.priority_normal_done;
                }
            case PRIORITY_HIGH:
                if(getStatus() == STATUS_CURRENT || getStatus() == STATUS_OVERDUE){
                    return R.color.priority_high;
                } else{
                    return R.color.priority_high_done;
                }
                default: return 0;
        }
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }

    public int getPriority() {
        return mPriority;
    }

    public void setPriority(int priority) {
        mPriority = priority;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public long getTimeStamp() {
        return mTimeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        mTimeStamp = timeStamp;
    }

    public int getDateStatus() {
        return dateStatus;
    }

    public void setDateStatus(int dateStatus) {
        this.dateStatus = dateStatus;
    }
}
