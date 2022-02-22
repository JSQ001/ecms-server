package com.eicas.ecms.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author zyt
 * @since 2022-01-11
 */
@Getter
@Setter
@Data
@TableName("info_cron_crawl")
@ApiModel(value = "CronCrawl对象", description = "")
public class CronCrawl implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("采集规则id")
    @TableField("rule_id")
    private String ruleId;

    @ApiModelProperty("任务名称")
    @TableField("jName")
    private String jName;

    @ApiModelProperty("任务组")
    @TableField("jGroup")
    private String jGroup;

    @ApiModelProperty("触发器名称")
    @TableField("tName")
    private String tName;

    @ApiModelProperty("触发器组")
    @TableField("tGroup")
    private String tGroup;

    @ApiModelProperty("cron表达式，定时采集")
    @TableField("cron")
    private String cron;

    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private String createdBy;

    @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;

    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
    private String updatedBy;

    @ApiModelProperty("逻辑删除 0:未删除 1:删除")
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    private Integer isDeleted;



}
