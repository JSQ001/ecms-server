package com.eicas.ecms.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 文章表
 * </p>
 *
 * @author jsq
 * @since 2021-10-28
 */
@Getter
@Setter
@Data
@TableName("info_ecms")
@ApiModel(value = "Ecms对象", description = "新闻表")
public class Ecms implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("所属栏目id")
    @TableField("column_id")
    private String columnId;

    @ApiModelProperty("内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("副标题")
    @TableField("sub_title")
    private String subTitle;

    @ApiModelProperty("附件")
    @TableField("attachment")
    private String attachment;

    @ApiModelProperty("文章跳转链接地址")
    @TableField("link_url")
    private String linkUrl;

    @ApiModelProperty("关键字")
    @TableField("keyword")
    private String keyword;

    @ApiModelProperty("描述")
    @TableField("remarks")
    private String remarks;

    @ApiModelProperty("文章图片")
    @TableField("img")
    private String img;

    @ApiModelProperty("文章来源")
    @TableField("source")
    private String source;

    @ApiModelProperty("文章作者")
    @TableField("author")
    private String author;

    @ApiModelProperty("类型")
    @TableField("type")
    private String type;

    @ApiModelProperty("状态 0：未审核 1：审核通过 2：审核不通过")
    @TableField(value = "column_status", fill = FieldFill.INSERT)
    private Integer status;

    @ApiModelProperty("自定义顺序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("点击次数")
    @TableField("hit_times")
    private Integer hitTimes;

    @ApiModelProperty("创建时间")
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @ApiModelProperty("更新时间")
    @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;

    @ApiModelProperty("创建人")
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private String createdBy;

    @ApiModelProperty("更新人")
    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
    private String updatedBy;

    @ApiModelProperty("逻辑删除")
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    private Boolean deleted ;

    @TableField(exist = false)
    private String columnName;

    @TableField(value = "focus_img", fill = FieldFill.INSERT)
    private Boolean focusImg;

    @ApiModelProperty("留言")
    @TableField("tips")
    private String tips;

    @ApiModelProperty("审核")
    @TableField(exist = false)
    private Boolean approved;
}
