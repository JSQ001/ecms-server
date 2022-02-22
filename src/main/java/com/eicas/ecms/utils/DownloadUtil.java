package com.eicas.ecms.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
public class DownloadUtil {


    public static String download(String urlString, String filename,String savePath,String nginxPath) throws Exception {
        URL url = new URL(urlString);

        URLConnection con = url.openConnection();

        con.setConnectTimeout(5*1000);

        InputStream is = con.getInputStream();

        String[] split = filename.split("\\.");
        String ext = "." + split[1];

        String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + UUID.randomUUID().toString().replace("-", "") + ext;
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String catalogName = savePath + "/" + format;

        byte[] bs = new byte[1024];

        int len;

        File sf = new File(catalogName);

        if (!sf.exists()) {
            sf.mkdirs();
        }
        OutputStream os = new FileOutputStream(sf.getPath() + "/" + newFileName);

        while ((len = is.read(bs)) != -1) {
            os.write(bs,0,len);
        }
        String urlFile = "download/"+format+ "/" + newFileName;
//        new PicUploadController().upload((MultipartFile) is);
        os.close();
        is.close();
        log.info("图片下载成功");
        return urlFile;
    }


}
