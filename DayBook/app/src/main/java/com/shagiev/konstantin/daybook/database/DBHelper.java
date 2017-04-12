package com.shagiev.konstantin.daybook.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.shagiev.konstantin.daybook.model.Task;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "daybook_database";
    public static final String TASKS_TABLE = "tasks_table";

    public static final String TASK_TITLE_COLUMN = "task_title";
    public static final String TASK_DATE_COLUMN = "task_date";
    public static final String TASK_PRIORITY_COLUMN = "task_priority";
    public static final String TASK_STATUS_COLUMN = "task_status";
    public static final String TASK_TIMESTAMP_COLUMN = "task_timestamp";

    public static final String SELECTION_STATUS = TASK_STATUS_COLUMN + " = ?";
    public static final String SELECTION_TIMESTAMP = TASK_TIMESTAMP_COLUMN + " = ?";

    private DBManager mDBManager;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mDBManager = new DBManager(getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TASKS_TABLE + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, " + TASK_TITLE_COLUMN + " TEXT NOT NULL, "+ TASK_DATE_COLUMN + " LONG, "+
        TASK_PRIORITY_COLUMN +" INTEGER, " + TASK_STATUS_COLUMN + " INTEGER, " + TASK_TIMESTAMP_COLUMN +
        " LONG);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TASKS_TABLE);
        onCreate(db);
    }

    public void saveTask(Task task){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_TITLE_COLUMN, task.getTitle());
        contentValues.put(TASK_DATE_COLUMN, task.getDate());
        contentValues.put(TASK_PRIORITY_COLUMN, task.getPriority());
        contentValues.put(TASK_STATUS_COLUMN, task.getStatus());
        contentValues.put(TASK_TIMESTAMP_COLUMN, task.getTimeStamp());

        getWritableDatabase().insert(TASKS_TABLE, null, contentValues);
     }

    public DBManager getDBManager() {
        return mDBManager;
    }

    public void removeTask(long timestamp){
        getWritableDatabase().delete(TASKS_TABLE, SELECTION_TIMESTAMP, new String[]{Long.toString(timestamp)} );
    }
}
