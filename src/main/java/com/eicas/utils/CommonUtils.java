package com.eicas.utils;

import java.io.File;

public class CommonUtils {

    /**
     * 修剪url路径，添加斜杆结尾
     *
     * @param url 文件路径
     * @return 修剪后路径
     */
    public static String trimUrl(String url){
        if(!url.endsWith("/")){
            url += "/";
        }
        return url;
    }
}
