package com.shagiev.konstantin.daybook.model;


public class Task implements Item {

    private String mTitle;
    private long mDate;

    public Task() {
    }

    public Task(String title, long date) {
        mTitle = title;
        mDate = date;
    }

    @Override
    public boolean isTask() {
        return true;
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
}
