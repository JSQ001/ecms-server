package com.eicas.cms.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 栏目表
 * </p>
 *
 * @author jsq
 * @since 2022-03-02
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("cms_column")
@ApiModel(value = "Column对象", description = "栏目表")
public class Column extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("所属父栏目id")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty("栏目code")
    @TableField("code")
    @NotNull(message = "栏目名称不能为空")
    private String code;

    @ApiModelProperty("栏目名称")
    @TableField("name")
    @NotNull(message = "栏目名称不能为空")
    private String name;

    @ApiModelProperty("栏目描述")
    @TableField("description")
    private String description;

    @ApiModelProperty("栏目封面图片地址")
    @TableField("cover_img_url")
    private String coverImgUrl;

    @ApiModelProperty("栏目标记属性（扩展用）")
    @TableField("flag")
    private String flag;

    @ApiModelProperty("栏目路径")
    @TableField("path")
    private String path;

    @ApiModelProperty("栏目类型：0-内部栏目，1-外部栏目")
    @TableField("type")
    private Integer type;

    @ApiModelProperty("关键字")
    @TableField("keyword")
    private String keyword;

    @ApiModelProperty("自定义链接")
    @TableField("custom_url")
    private String customUrl;

    @ApiModelProperty("栏目上线时间")
    @TableField("publish_time")
    private LocalDateTime publishTime;

    @ApiModelProperty("自定义顺序")
    @TableField("sort_order")
    private Integer sortOrder;

    @ApiModelProperty("内容页模板")
    @TableField("content_template_url")
    private String contentTemplateUrl;

    @ApiModelProperty("列表页模板")
    @TableField("list_template_url")
    private String listTemplateUrl;

    @TableField(exist = false)
    private List<Column> children;

    @TableField(exist = false)
    private Column parent;

    public void setChildren(List<Column> children) {
        this.children = children.size() == 0 ? null : children;
    }
}
