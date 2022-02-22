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
 * 文章表
 * </p>
 *
 * @author jsq
 * @since 2021-10-28
 */
@Getter
@Setter
@TableName("info_ecms_rule")
@ApiModel(value = "ArticleRule对象", description = "文章规则表")
public class ArticleRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("规则id")
    @TableField("rule_id")
    private String ruleId;

    @ApiModelProperty("所属栏目id")
    @TableField("column_id")
    private String columnId;

    @ApiModelProperty("文章id")
    @TableField("ecmas_id")
    private String articleId;

    @ApiModelProperty("图片数量")
    @TableField("img_num")
    private Integer imgNum;


    @ApiModelProperty("创建时间")
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private String createdTime;

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
    @TableField(value = "deleted", fill = FieldFill.INSERT)
    private Boolean deleted;

    @ApiModelProperty("状态")
    @TableField("ecms_status")
    private Integer status;


}
