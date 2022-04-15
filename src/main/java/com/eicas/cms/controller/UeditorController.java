package com.eicas.cms.controller;

import com.alibaba.fastjson.JSON;
import com.eicas.cms.pojo.vo.UploadImageRrsult;
import com.eicas.cms.utils.UploadFileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lee.common.ueditor.ActionEnter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于处理关于ueditor插件相关的请求
 *
 * @author Guoqing
 * @date 2017-11-29
 */
@Slf4j
@RestController
@RequestMapping("/ueditor")
public class UeditorController {
    @Value("${filePath.loadPath}")
    private String loadPath;


    @Value("${filePath.image}")
    private  String imagePath;


    @GetMapping(value = "/exec")
    @ResponseBody
    public String exec(HttpServletRequest request) throws UnsupportedEncodingException, JSONException {
        String config = "'{\"imageActionName\":\"fileLoad\",\"imageFieldName\":\"file\",\"imageMaxSize\":2048000,\"imageAllowFiles\":[\".png\",\".jpg\",\".jpeg\",\".gif\",\".bmp\"],\"imageCompressEnable\":true,\"imageCompressBorder\":1600,\"imageInsertAlign\":\"none\",\"imageUrlPrefix\":\"\",\"localSavePathPrefix\":\"/file\",\"imagePathFormat\":\"/file/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\",\"catcherLocalDomain\":[\"127.0.0.1\",\"localhost\",\"img.baidu.com\"],\"catcherActionName\":\"catchimage\",\"catcherFieldName\":\"source\",\"catcherPathFormat\":\"/file/{yyyy}{mm}{dd}/{time}{rand:6}\",\"catcherUrlPrefix\":\"\",\"catcherMaxSize\":2048000,\"catcherAllowFiles\":[\".png\",\".jpg\",\".jpeg\",\".gif\",\".bmp\"],\"catchRemoteImageEnable\":false,\"fileActionName\":\"fileLoad\",\"fileFieldName\":\"file\",\"filePathFormat\":\"/file/{yyyy}{mm}{dd}/{time}{rand:6}\",\"fileUrlPrefix\":\"\",\"fileMaxSize\":51200000,\"fileAllowFiles\":[\".png\",\".jpg\",\".jpeg\",\".gif\",\".bmp\",\".flv\",\".swf\",\".mkv\",\".avi\",\".rm\",\".rmvb\",\".mpeg\",\".mpg\",\".ogg\",\".ogv\",\".mov\",\".wmv\",\".mp4\",\".webm\",\".mp3\",\".wav\",\".mid\",\".rar\",\".zip\",\".tar\",\".gz\",\".7z\",\".bz2\",\".cab\",\".iso\",\".doc\",\".docx\",\".xls\",\".xlsx\",\".ppt\",\".pptx\",\".pdf\",\".txt\",\".md\",\".xml\"]}'";

        return config;
    }

    @GetMapping(value = "/exec1")
    @ResponseBody
    public Object exec1(HttpServletRequest request) throws UnsupportedEncodingException, JSONException {

        request.setCharacterEncoding("utf-8");
        //String rootPath = request.getRealPath("/");
        String rootPath = System.getProperty("user.dir") + "/src/main/";
        log.info("**************************");
        log.info(rootPath);
        String configStr = new ActionEnter(request, rootPath).exec();

        ObjectMapper objectMapper = new ObjectMapper();
        Map map = JSON.parseObject(new ActionEnter(request, rootPath).exec());

        System.out.println("configStr=" + configStr);
        return JSON.parse(new ActionEnter(request, rootPath).exec());
    }

    @PostMapping(value = "/exec")
    @ResponseBody
    public UploadImageRrsult exec(HttpServletRequest request, MultipartFile file) throws IOException, UnsupportedEncodingException, JSONException {
        return UploadFileUtil.updateFile(request, file, loadPath,imagePath);
    }
}