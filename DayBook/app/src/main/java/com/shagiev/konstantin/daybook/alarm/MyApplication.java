package com.shagiev.konstantin.daybook.alarm;


import android.app.Application;


/**
 * Класс для проверки видимости активности приложения
 */
public class MyApplication extends Application {

    private static boolean sActivityVisible;

    public static boolean ismActivityVisible() {
        return sActivityVisible;
    }

    public static void activityResumed(){
        sActivityVisible = true;
    }
    public static void activityPaused(){
        sActivityVisible = false;
    }
}
