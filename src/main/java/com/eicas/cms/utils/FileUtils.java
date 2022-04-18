package com.eicas.cms.utils;

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
public class FileUtils {
    /**
     * 下载图片保存至服务器
     *
     * @param imageUrl      图片URL
     * @param filename      图片名称
     * @param storeRootPath 图片保存根路径
     * @param imageMapping  图片映射根路径
     * @return 图片映射全路径
     */

    public static String download(String imageUrl, String filename, String storeRootPath, String imageMapping) {
        String newImageUrl;
        try {
            URL url = new URL(imageUrl);
            URLConnection con = url.openConnection();
            con.setConnectTimeout(5 * 1000);
            InputStream is = con.getInputStream();


            String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String catalogName = storeRootPath + "/" + format;
            File storePath = new File(catalogName);
            if (!storePath.exists()) {
                storePath.mkdirs();
            }
            String[] split = filename.split("\\.");
            String ext = "." + split[split.length - 1];
            String newFilename = UUID.randomUUID() + ext;
            File targetFile = new File(storePath, newFilename);

            byte[] buffer = new byte[1024];
            int len;

            OutputStream os = new FileOutputStream(targetFile);

            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            newImageUrl = imageMapping + format + "/" + newFilename;
            os.close();
            is.close();
            log.debug("成功下载图片:" + newImageUrl);
        } catch (Exception e) {
            newImageUrl = null;
            log.error(e.getMessage());
        }
        return newImageUrl;
    }

}
