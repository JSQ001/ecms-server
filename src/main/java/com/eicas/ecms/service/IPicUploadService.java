package com.eicas.ecms.service;

import com.eicas.ecms.pojo.PicUploadResult;
import org.springframework.web.multipart.MultipartFile;

public interface IPicUploadService {

    PicUploadResult upload(MultipartFile uploadFile);
}
