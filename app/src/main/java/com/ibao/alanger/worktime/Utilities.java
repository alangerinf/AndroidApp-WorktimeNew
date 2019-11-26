package com.ibao.alanger.worktime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Utilities {

    public static String getDateTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }


    public static String getHour(){
        Calendar calendar = new GregorianCalendar();

        int hour  = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        String strHour = "";
        String strMinute = "";

        if(hour<10){
            strHour = "0"+hour;
        }else {
            strHour = ""+hour;
        }

        if(minute<10){
            strMinute = "0"+minute;
        }else {
            strMinute = ""+minute;
        }

        return strHour+":"+strMinute;
    }

}
