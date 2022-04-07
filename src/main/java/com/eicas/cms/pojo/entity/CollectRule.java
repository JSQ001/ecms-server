package com.eicas.cms.pojo.entity;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 采集规则表
*
* @author jsq
* @since 2022-03-06
*/

@Getter
@Setter
@Accessors(chain = true)
@TableName("cms_collect_rule")
@ApiModel(value = "采集规则表对象", description = "采集规则表")
public class CollectRule extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("节点名称")
    @TableField("name")
    @NotNull(message = "name不能为空")
    private String name;

    @ApiModelProperty("规则类型：1-XPath，2-正则表达式")
    @TableField("rule_type")
    @NotNull(message = "ruleType不能为空")
    private Integer ruleType;

    @ApiModelProperty("入库栏目id")
    @TableField("column_id")
    @NotNull(message = "columnId不能为空")
    private Long columnId;

    @ApiModelProperty("采集来源")
    @TableField("source")
    private String source;

    @ApiModelProperty("采集地址地址")
    @TableField("collect_url")
    @NotNull(message = "collectUrl不能为空")
    private String collectUrl;

    @ApiModelProperty("采集时间")
    @TableField("collect_time")
    private String collectTime;

    @ApiModelProperty("采集列表规则")
    @TableField("links_rule")
    @NotNull(message = "linksRule不能为空")
    private String linksRule;

    @ApiModelProperty("标题规则")
    @TableField("title_rule")
    @NotNull(message = "titleRule不能为空")
    private String titleRule;

    @ApiModelProperty("副标题规则")
    @TableField("sub_title_rule")
    private String subTitleRule;

    @ApiModelProperty("发布时间规则")
    @TableField("pub_time_rule")
    private String pubTimeRule;

    @ApiModelProperty("文章摘要规则")
    @TableField("essential_rule")
    private String essentialRule;

    @ApiModelProperty("作者规则")
    @TableField("author_rule")
    private String authorRule;

    @ApiModelProperty("正文规则")
    @NotNull(message = "contentRule不能为空")
    @TableField("content_rule")
    private String contentRule;

    @ApiModelProperty("备注")
    @TableField("remarks")
    private String remarks;

    @ApiModelProperty("是否自动采集：0-非自动，1-自动")
    @TableField("is_auto")
    private Boolean isAuto;

    @ApiModelProperty("自动采集时间")
    @TableField("auto_collect_time")
    private LocalDateTime autoCollectTime;

    @TableField(exist = false)
    private String columnName;


    @TableField("is_flag")
    private int  isFlag;

    @TableField("collect_number")
    private int collectNumber;



}