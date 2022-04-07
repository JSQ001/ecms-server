package com.eicas.cms.pojo.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class MemorbiliaVo {

    @ApiModelProperty("大事件id")
    private Long id;

    @ApiModelProperty("所属栏目id")
    private Long columnId;

    @ApiModelProperty("文章标题")
    @NotNull(message = "文章标题不能为空")
    private String title;

    @ApiModelProperty("发布时间")
    private LocalDateTime publishTime;

    @ApiModelProperty("标题图片")
    private String coverImgUrl;

    @ApiModelProperty("内容")
    private String content;


    @ApiModelProperty("开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("结束时间")
    private LocalDateTime endTime;


}
