//package com.eicas;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.Month;
//import java.time.format.DateTimeFormatter;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@RunWith(SpringRunner.class)
//public class smallT {
//    public static final String CRON_DATE_FORMAT="ss mm HH * * ? ";
//    @Test
//    public void t1() {
//        LocalDateTime now = LocalDateTime.now();
//        String format = DateTimeFormatter.ofPattern(CRON_DATE_FORMAT).format(now);
//        System.out.println(format);
//    }
//
//    @Test
//    public void t2() {
//
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime localDateTime1 = LocalDateTime.of(2019,  10, 14, 46, 56);
//        System.out.println(localDateTime1);
////// 获取LocalDate
////        LocalDate localDate2 = localDateTime.toLocalDate();
////// 获取LocalTime
////        LocalTime localTime2 = localDateTime.toLocalTime();
//    }
//}
