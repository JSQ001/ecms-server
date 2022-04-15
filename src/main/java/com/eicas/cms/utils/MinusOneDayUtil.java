package com.eicas.cms.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MinusOneDayUtil {

    public  static  String minusOneDay(String daystr) {
        try {


            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            String[] timeStr = daystr.split("-");
            Calendar calendar = Calendar.getInstance();
            int year = Integer.valueOf(timeStr[0]);
            int month = Integer.valueOf(timeStr[1]);
            int day = Integer.valueOf(timeStr[2]);
            if (day <= 1) {
                String date = null;
                if (month > 1) {
                    month--;
                    Calendar c = Calendar.getInstance();
                    c.set(year, month, 0);
                    Date parse = dateFormat.parse(year + "-" + month + "-" + c.get(c.DAY_OF_MONTH));
                    date = dateFormat.format(parse);
                } else if (month == 1) {
                    year--;
                    date = year + "-12-31";
                }
                return date;
            }
            Date date = dateFormat.parse(daystr);
            calendar.setTime(date);
            calendar.add(calendar.DATE, -1);
            return dateFormat.format(calendar.getTime());

        }
     catch (ParseException e){
           return "";
    }


}}
