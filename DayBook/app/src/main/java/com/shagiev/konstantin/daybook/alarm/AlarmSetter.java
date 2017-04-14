package com.shagiev.konstantin.daybook.alarm;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.shagiev.konstantin.daybook.database.DBHelper;
import com.shagiev.konstantin.daybook.model.Task;

import java.util.ArrayList;
import java.util.List;


/**
 * Устанавливает оповещения после перезагрузки устройства
 */
public class AlarmSetter extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DBHelper dbHelper = new DBHelper(context);

        AlarmHelper.getInstance().init(context);
        AlarmHelper alarmHelper = AlarmHelper.getInstance();

        List<Task> tasks = new ArrayList<>();
        tasks.addAll(dbHelper.getDBManager().getTasks(DBHelper.SELECTION_STATUS + " OR "
                        + DBHelper.SELECTION_STATUS, new String[]{Integer.toString(Task.STATUS_CURRENT), Integer.toString(Task.STATUS_OVERDUE)},
                DBHelper.TASK_DATE_COLUMN));
        for(Task task:tasks){
            if (task.getDate() != 0){
                alarmHelper.setAlarm(task);
            }
        }
    }
}
