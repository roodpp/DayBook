package com.shagiev.konstantin.daybook.alarm;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.shagiev.konstantin.daybook.model.Task;

/**
 * Синглтон, отвечает за управление оповещениями
 */
public class AlarmHelper {

    private static AlarmHelper sInstance;
    private Context mContext;
    private AlarmManager mAlarmManager;

    private AlarmHelper() {
    }

    public static AlarmHelper getInstance(){
        if(sInstance == null){
            sInstance = new AlarmHelper();
        }
        return sInstance;
    }
    public void init(Context context){
        mContext = context;
        mAlarmManager = (AlarmManager) context.getApplicationContext()
                .getSystemService(Context.ALARM_SERVICE);

    }
    public void setAlarm(Task task){
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        intent.putExtra("title", task.getTitle());
        intent.putExtra("timestamp", task.getTimeStamp());
        intent.putExtra("color", task.getPriorityColor());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext.getApplicationContext(),
                (int) task.getTimeStamp(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mAlarmManager.set(AlarmManager.RTC_WAKEUP, task.getDate(), pendingIntent);
    }

    public void removeAlarm(long taskTimestamp){
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext,
                (int) taskTimestamp, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mAlarmManager.cancel(pendingIntent);
    }
}
