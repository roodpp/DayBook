package com.shagiev.konstantin.daybook.database;


import android.database.Cursor;
import android.database.CursorWrapper;

import com.shagiev.konstantin.daybook.model.Task;

public class TaskCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Task getTask(){
        String title = getString(getColumnIndex(DBHelper.TASK_TITLE_COLUMN));
        long date = getLong(getColumnIndex(DBHelper.TASK_DATE_COLUMN));
        int priority = getInt(getColumnIndex(DBHelper.TASK_PRIORITY_COLUMN));
        int status =  getInt(getColumnIndex(DBHelper.TASK_STATUS_COLUMN));
        long timestamp = getLong(getColumnIndex(DBHelper.TASK_TIMESTAMP_COLUMN));

        Task task = new Task(title, date, priority, status, timestamp);

        return task;
    }
}
