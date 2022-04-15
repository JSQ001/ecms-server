package com.eicas.cms.controller;

import com.eicas.cms.crawling.StorePipeline;
import com.eicas.cms.crawling.Test;
import com.eicas.cms.crawling.WebDriverDownloader;
import liquibase.util.NowAndTodayUtil;
import org.springframework.scheduling.support.CronTrigger;
import us.codecraft.webmagic.Spider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Slf4j
public class TestA {



    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("new Date()=="+sdf.format(new Date()));
        System.out.println(minusOneDay(sdf.format(new Date())));

    }

    public  static  String minusOneDay(String daystr) throws ParseException {

        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-mm-dd");
        String[] timeStr=daystr.split("-");
        Calendar calendar=Calendar.getInstance();
        int year=Integer.valueOf(timeStr[0]);
        int month=Integer.valueOf(timeStr[1]);
        int day=Integer.valueOf(timeStr[2]);
        if (day<=1){
            String date=null;
            if (month>1){
                month--;
                Calendar c=Calendar.getInstance();
                c.set(year,month,0);
                Date parse=dateFormat.parse(year+"-"+month+"-"+c.get(c.DAY_OF_MONTH));
                date=dateFormat.format(parse);
            }
            else if (month==1){
                   year--;
                   date=year+"-12-31";
            }
            return date;
        }
        Date date=dateFormat.parse(daystr);
        calendar.setTime(date);
        calendar.add(calendar.DATE,-1);
        return  dateFormat.format(calendar.getTime());

    }



}
