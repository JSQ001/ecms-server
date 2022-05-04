package com.eicas.utils;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommonUtils {

    /**
     * 修剪url路径，添加斜杆结尾
     * @param url 文件路径
     * @return 修剪后路径
     */
    public static String trimUrl(String url){
        if(!url.endsWith("/")){
            url += "/";
        }
        return url;
    }

    /**
     * 将LocalDateTime转换为Cron表达式
     * @param ldt
     * @return
     */
    public static String LocalDateTimeConvertToCron(LocalDateTime ldt) {

        final String CRON_DATE_FORMAT = "ss mm HH * * *";
        DateTimeFormatter df = DateTimeFormatter.ofPattern(CRON_DATE_FORMAT);
        return df.format(ldt);
    }
}
