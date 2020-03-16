package com.example.orderspot_general.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Util {
    public static Random random = new Random();

    public static boolean isString(String string){
        if(string != null)
            return true;
        return false;
    }

    public  static  boolean isSuccess(String success){
        if(success.equals("True"))
            return true;
        return false;
    }

    public static  boolean isSameString(String string1, String string2){
        if(string1.equals(string2))
            return true;
        return false;
    }

    public static Calendar getCalendarByString(String dateString){
        Date date = null;
        Calendar calendar = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss");
            date = simpleDateFormat.parse(dateString);
            calendar = getCalendarByDate(date);
        }catch (Exception e){

        }
        return calendar;
    }

    public static Calendar getCalendarByDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static int getRandomInt(int size){
        return random.nextInt(size);
    }

    public static boolean isCalendarOfPast(Calendar toDayCalendar, Calendar calendar){
        Long dateDifference = (toDayCalendar.getTimeInMillis() - calendar.getTimeInMillis()) / 1000 / (24*60*60) ;
        if(dateDifference < 0){
            return false;
        }
        return true;
    }
}
