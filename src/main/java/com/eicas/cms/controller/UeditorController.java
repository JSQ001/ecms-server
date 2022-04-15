package com.eicas.cms.controller;

import com.alibaba.fastjson.JSON;
import com.eicas.cms.pojo.vo.UploadImageRrsult;
import com.eicas.cms.utils.UploadFileUtil;
import com.lee.common.ueditor.ActionEnter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
    public Object exec(HttpServletRequest request) throws UnsupportedEncodingException, JSONException {
        request.setCharacterEncoding("utf-8");
        //String rootPath = request.getRealPath("/");
        String rootPath = System.getProperty("user.dir") + "/src/main/";
        log.info("**************************");
        log.info(rootPath);
        String configStr = new ActionEnter(request, rootPath).exec();
        System.out.println("configStr=" + configStr);
        return JSON.parse(new ActionEnter(request, rootPath).exec());
    }

    @PostMapping(value = "/exec")
    @ResponseBody
    public UploadImageRrsult exec(HttpServletRequest request, MultipartFile file) throws IOException, UnsupportedEncodingException, JSONException {
        return UploadFileUtil.updateFile(request, file, loadPath,imagePath);
    }
}