package com.eicas.ecms.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PicUploadResult {
    // 文件路径
    private String url;
    private String fileName;

}
