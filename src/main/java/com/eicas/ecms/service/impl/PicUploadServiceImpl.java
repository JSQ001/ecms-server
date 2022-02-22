package com.eicas.ecms.service.impl;

import com.eicas.ecms.pojo.PicUploadResult;
import com.eicas.ecms.service.IPicUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 文件上传工具类
 */
@Service
@Slf4j
public class PicUploadServiceImpl implements IPicUploadService {
    //网关服务器IP地址
    @Value("${file.path}")
    private String filePath;

    //nginx路径地址
    @Value("${file.nginxPath}")
    private String nginxPath;

    /**
     * 文件上传
     *
     * @param uploadFile 文件对象
     * @return 上传结果
     */
    @Override
    public PicUploadResult upload(MultipartFile uploadFile) {
        PicUploadResult fileUploadResult = null;
        try {
            //文件类型
            String contentType = uploadFile.getContentType();
            String originalFilename = uploadFile.getOriginalFilename();
            log.info("文件名字-------------------------{}",originalFilename);
            //获取文件后缀名
            String[] split = new String[0];
            if (contentType != null) {
                split = contentType.split("/");
            }
            String ext = "." + split[1];
            //获取下载路径url
            String url = URLDecoder.decode(filePath,"UTF-8");

            //创建新文件名字
            String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + UUID.randomUUID().toString().replace("-", "") + ext;
            String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String catalogName = url + "/" + format;


            File fileDir = new File(catalogName);
            if (!fileDir.exists()) {
                System.out.println("新建文件夹"+fileDir.mkdirs());
            }

            String urlFile = nginxPath+format+ "/" + newFileName;
            fileUploadResult = new PicUploadResult();
            fileUploadResult.setUrl(urlFile);
            fileUploadResult.setFileName(originalFilename);
            //上传文件
            uploadFile.transferTo(new File(catalogName, newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileUploadResult;
    }


}
