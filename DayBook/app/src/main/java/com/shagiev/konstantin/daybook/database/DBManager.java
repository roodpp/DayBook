package com.shagiev.konstantin.daybook.database;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.shagiev.konstantin.daybook.model.Task;

import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private SQLiteDatabase mSQLiteDatabase;

    public DBManager(SQLiteDatabase SQLiteDatabase) {
        mSQLiteDatabase = SQLiteDatabase;
    }


    public List<Task> getTasks(String selection, String[] selectionArgs, String orderBy){
        List<Task> tasks = new ArrayList<>();

        TaskCursorWrapper cursor = queryTasks(selection, selectionArgs, orderBy);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                tasks.add(cursor.getTask());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return tasks;
    }


    public void updateTitle(long timestamp, String title){
        update(DBHelper.TASK_TITLE_COLUMN, timestamp, title);
    }
    public void updateDate(long timestamp, long date){
        update(DBHelper.TASK_DATE_COLUMN, timestamp, date);
    }
    public void updatePriority(long timestamp, int priority){
        update(DBHelper.TASK_PRIORITY_COLUMN, timestamp, priority);
    }
    public void updateStatus(long timestamp, int status){
        update(DBHelper.TASK_STATUS_COLUMN, timestamp, status);
    }

    public void updateTask(Task task){
        updateTitle(task.getTimeStamp(), task.getTitle());
        updateDate(task.getTimeStamp(), task.getDate());
        updatePriority(task.getTimeStamp(), task.getPriority());
        updateStatus(task.getTimeStamp(), task.getStatus());
    }

    public void update(String column, long key, String value){
        ContentValues cv = new ContentValues();
        cv.put(column, value);
        mSQLiteDatabase.update(DBHelper.TASKS_TABLE, cv, DBHelper.TASK_TIMESTAMP_COLUMN + " = " + key, null);
    }

    public void update(String column, long key, long value){
        ContentValues cv = new ContentValues();
        cv.put(column, value);
        mSQLiteDatabase.update(DBHelper.TASKS_TABLE, cv, DBHelper.TASK_TIMESTAMP_COLUMN + " = " + key, null);
    }


    private TaskCursorWrapper queryTasks(String selection, String[] selectionArgs, String orderBy){
        Cursor cursor = mSQLiteDatabase.query(
                DBHelper.TASKS_TABLE,
                null,
                selection,
                selectionArgs,
                null,
                null,
                orderBy
        );
        return new TaskCursorWrapper(cursor);
    }
}
