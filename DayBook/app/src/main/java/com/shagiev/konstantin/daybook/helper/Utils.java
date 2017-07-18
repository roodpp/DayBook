package com.shagiev.konstantin.daybook.helper;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {


    public static String getDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        return dateFormat.format(date);
    }

    public static String getTime(Date time){
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        String strTime = (df.format("HH:mm", time)).toString();
        return strTime;
    }

    public static String getFullDate(long date) {
        SimpleDateFormat fullDateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");
        return fullDateFormat.format(date);
    }

    public static String getFullDateLocale(Date date){
        Locale locale = new Locale("ru","RU");
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, locale);
        String strDate = df.format(date);
        return strDate + " " + getTime(date);
    }
}
