package com.eicas.cms.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 文章信息表
 * </p>
 *
 * @author jsq
 * @since 2022-03-05
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("cms_article")
@ApiModel(value = "Article对象", description = "文章信息表")
public class Article extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("所属栏目id")
    @TableField("column_id")
    @NotNull(message = "栏目id不能为空")
    private Long columnId;

    @ApiModelProperty("文章标题")
    @TableField("title")
    @NotNull(message = "文章标题不能为空")
    private String title;

    @ApiModelProperty("副标题")
    @TableField("sub_title")
    private String subTitle;

    @ApiModelProperty("关键字")
    @TableField("keyword")
    private String keyword;

    @ApiModelProperty("文章摘要")
    @TableField("essential")
    private String essential;

    @ApiModelProperty("描述")
    @TableField("remarks")
    private String remarks;

    @ApiModelProperty("内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("标题图片")
    @TableField("cover_img_url")
    private String coverImgUrl;

    @ApiModelProperty("跳转链接地址")
    @TableField("link_url")
    private String linkUrl;

    @ApiModelProperty("类型（扩展使用）")
    @TableField("type")
    private String type;

    @ApiModelProperty("作者")
    @TableField("author")
    private String author;

    @ApiModelProperty("来源")
    @TableField("source")
    private String source;

    @ApiModelProperty("审核批注")
    @TableField("audit_comments")
    private String auditComments;

    @ApiModelProperty("审核人")
    @TableField("audit_user_id")
    private Long auditUserId;

    @ApiModelProperty("审核时间")
    @TableField("audit_time")
    private LocalDateTime auditTime;

    @ApiModelProperty("编辑状态：0-草稿，1-待审核，2-审核通过，3-审核不通过")
    @TableField("state")
    private Integer state;

    @ApiModelProperty("是否显示：0-不显示，1-显示")
    @TableField("is_show")
    private Boolean isShow;

    @ApiModelProperty("是否焦点图显示")
    @TableField("is_focus")
    private Boolean focus;

    @ApiModelProperty("是否推荐")
    @TableField("is_recommended")
    private Boolean recommended;

    @ApiModelProperty("是否置顶")
    @TableField("is_top")
    private Boolean top;

    @ApiModelProperty("是否大事")
    @TableField("is_major")
    private Boolean major;

    @ApiModelProperty("发布时间")
    @TableField(value = "publish_time", fill = FieldFill.INSERT)
    private LocalDateTime publishTime;

    @ApiModelProperty("自定义顺序")
    @TableField("sort_order")
    private Integer sortOrder;

    @ApiModelProperty("点击次数")
    @TableField("hit_nums")
    private Integer hitNums;


    @TableField(exist = false)
    private LocalDateTime startTime;

    @TableField(exist = false)
    private LocalDateTime endTime;

    @TableField(exist = false)
    private String columnName;
}
