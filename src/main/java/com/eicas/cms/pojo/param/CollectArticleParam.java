package com.eicas.cms.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class CollectArticleParam {
    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "是否入库")
    private Boolean received;

    @ApiModelProperty(value = "栏目id")
    private Long catalogId;

    @ApiModelProperty(value = "发布时间-起始")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginPublishTime;

    @ApiModelProperty(value = "发布时间-截止")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endPublishTime;
}
