package com.shagiev.konstantin.daybook.alarm;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;

import com.shagiev.konstantin.daybook.R;
import com.shagiev.konstantin.daybook.activities.MainActivity;

/**
 * Класс, отвечающий за создание уведомления, после получения оповещения
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String title = intent.getStringExtra("title");
        long timestamp = intent.getLongExtra("timestamp", 0);
        int color = intent.getIntExtra("color", 0);

        Intent resultIntent = new Intent(context, MainActivity.class);

        if(MyApplication.ismActivityVisible()){
            resultIntent = intent;
        }

        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, (int) timestamp,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);

        notificationBuilder.setContentTitle(context.getResources().getString(R.string.app_name));
        notificationBuilder.setContentText(title);
        notificationBuilder.setColor(context.getResources().getColor(color));
        notificationBuilder.setSmallIcon(R.drawable.ic_checkbox_blank_circle_white_48dp);


        notificationBuilder.setDefaults(Notification.DEFAULT_ALL);
        notificationBuilder.setContentIntent(pendingIntent);

        Notification notification = notificationBuilder.build();
        notification.flags = notification.flags | Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) timestamp, notification);


    }
}
