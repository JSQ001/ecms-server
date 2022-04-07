package com.eicas.cms.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class NotifyVo {

    @ApiModelProperty("公告id")
    private Long id;

    @ApiModelProperty("公告状态0 草稿  2 发布")
    private  int state;

    @ApiModelProperty("所属栏目id")
    private Long columnId;

    @ApiModelProperty("公告标题")
    @NotNull(message = "公告标题不能为空")
    private String title;

    @ApiModelProperty("发布时间")
    private LocalDateTime publishTime;

    @ApiModelProperty("发布人")
    private String author;


    @ApiModelProperty("内容")
    private String content;


    @ApiModelProperty("开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("结束时间")
    private LocalDateTime endTime;




}
