package com.eicas.ecms.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 采集规则表
 * </p>
 *
 * @author jsq
 * @since 2021-11-09
 */
@Getter
@Setter
@TableName("info_collection_rule")
@ApiModel(value = "CollectionRule对象", description = "采集规则表")
public class CollectionRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("所属栏目id")
    @TableField("column_id")
    private String columnId;

    @ApiModelProperty("栏目名称")
    @TableField(exist = false)
    private String columnName;

    @ApiModelProperty("规则名称")
    @TableField("rule_name")
    private String ruleName;

    @ApiModelProperty("采集地址")
    @TableField("collection_path")
    private String collectionPath;

    @ApiModelProperty("采集页为直接内容")
    @TableField("is_content")
    private Boolean isContent;

    @ApiModelProperty("内容页面地址前缀")
    @TableField("content_address_prefix")
    private String contentAddressPrefix;

    @ApiModelProperty("图片/FLASH地址前缀")
    @TableField("img_address_prefix")
    private String imgAddressPrefix;

    @ApiModelProperty("副标题")
    @TableField("sub_title")
    private String subTitle;

    @ApiModelProperty("开始时间")
    @TableField("start_date")
    private LocalDateTime startDate;

    @ApiModelProperty("结束时间")
    @TableField("end_date")
    private LocalDateTime endDate;

    @ApiModelProperty("备注")
    @TableField("remarks")
    private String remarks;

    @ApiModelProperty("信息页链接规则")
    @TableField("info_page_rule")
    private String infoPageRule;

    @ApiModelProperty("标题图片规则")
    @TableField("title_picture_rule")
    private String titlePictureReg;

    @ApiModelProperty("图片地址前缀")
    @TableField("title_address_prefix")
    private String titleAddressPrefix;

    @ApiModelProperty("保存本地")
    @TableField("is_save_local")
    private Boolean saveLocal;

    @ApiModelProperty("标题规则")
    @TableField("title_rule")
    private String titleRule;

    @ApiModelProperty("副标题规则")
    @TableField("sub_title_rule")
    private String subTitleRule;

    @ApiModelProperty("发布时间规则")
    @TableField("publish_time")
    private String publishTime;

    @ApiModelProperty("内容简介规则")
    @TableField("content_validity_rule")
    private String contentValidityRule;

    @ApiModelProperty("作者规则")
    @TableField("author_rule")
    private String authorRule;

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


    /**
     * 爬取种类：0:正则 ; 1:XPath
     */
    @TableField("rule_type")
    @ApiModelProperty(value = "爬取种类")
    private String ruleType;

    @TableField("content_rule")
    @ApiModelProperty(value = "内容")
    private String contentRule;


    @ApiModelProperty("状态")
    @TableField("rule_status")
    private String ruleStatus;

    @TableField(exist = false)
    @ApiModelProperty("时间")
    private String cron;

    @TableField(exist = false)
    @ApiModelProperty("是否自动采集")
    private Boolean autoCollect;

    @TableField(exist = false)
    private LocalDateTime localDateTime;
    @TableField(exist = false)
    private String abstractRule;
}
