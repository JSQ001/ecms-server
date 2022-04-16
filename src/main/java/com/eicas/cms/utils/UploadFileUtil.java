package com.eicas.cms.utils;

import com.eicas.cms.pojo.vo.UploadImageRrsult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class UploadFileUtil {



    public  static UploadImageRrsult  updateFile(HttpServletRequest request, MultipartFile file,String loadPath,String imagePath) throws IOException {
        UploadImageRrsult uploadImageRrsult=new UploadImageRrsult();
        String realPath = request.getSession().getServletContext().getRealPath("/uploadFile");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String mk = sdf.format(new Date());

        String path =imagePath+mk+"/"; //ClassUtils.getDefaultClassLoader().getResource("").getPath() + "upload/" + mk + "/";

        File targetFile = new File(path);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream bos = null;
        try {


            UUID uuid=UUID.randomUUID();
            String filename=uuid.toString().replace("-","");

            String str=targetFile+"/"+filename+"."+file.getContentType().substring(6);


            bos = new FileOutputStream(str);
             bos.write(file.getBytes());

            uploadImageRrsult.setUrl(loadPath+mk + "/" + filename+"."+file.getContentType().substring(6));
            uploadImageRrsult.setTitle(filename+"."+file.getContentType().substring(6));
            uploadImageRrsult.setState("SUCCESS");
            uploadImageRrsult.setOrginal(filename+"."+file.getContentType().substring(6));
            return  uploadImageRrsult;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            uploadImageRrsult.setState("FAIL");
            return uploadImageRrsult;
        } finally {

            bos.flush();
            bos.close();

        }





    }

}
