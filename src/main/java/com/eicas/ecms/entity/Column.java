package com.eicas.ecms.entity;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 栏目表
 * </p>
 *
 * @author jsq
 * @since 2021-11-29
 */
@Getter
@Setter
@TableName("info_column")
@ApiModel(value = "Column对象", description = "栏目表")
public class Column implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("栏目名称")
    @TableField("column_name")
    private String columnName;

    @ApiModelProperty("栏目类型id")
    @TableField("column_type_id")
    private String columnTypeId;

    @ApiModelProperty("columnTypeName")
    @TableField(exist = false)
    private String columnTypeName;

    @ApiModelProperty("栏目路径")
    @TableField("path")
    private String path;

    @ApiModelProperty("顺序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("描述")
    @TableField("remarks")
    private String remarks;

    @ApiModelProperty("缩略图")
    @TableField("thumb")
    private String thumb;

    @ApiModelProperty("父栏目id")
    @TableField("parent_id")
    private String parentId;

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
    @TableField(value = "is_deleted",fill = FieldFill.INSERT_UPDATE )
    private Boolean deleted =false;

    @TableField(exist = false)
    private List<Column> children;

    public void setChildren(List<Column> children) {
        this.children = children.size() == 0 ? null : children;
    }
}
