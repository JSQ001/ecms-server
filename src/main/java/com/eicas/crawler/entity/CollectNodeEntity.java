package com.eicas.crawler.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 采集节点表
 * </p>
 *
 * @author osnudt
 * @since 2022-04-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cms_collect_node")
@ApiModel(value="CollectNode对象", description="采集节点表")
public class CollectNodeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "节点名称")
    private String name;

    @ApiModelProperty(value = "入库栏目id")
    @NotNull
    private Long catalogId;

    @ApiModelProperty(value = "采集来源")
    private String source;

    @ApiModelProperty(value = "规则类型：1-XPath，2-正则表达式")
    private Integer ruleType;

    @ApiModelProperty(value = "采集状态：0-未采集，2-采集中")
    private Integer state;

    @ApiModelProperty(value = "采集地址")
    @NotNull
    private String collectUrl;

    @ApiModelProperty(value = "采集列表规则")
    @NotNull
    private String linksRule;

    @ApiModelProperty(value = "标题规则")
    @NotNull
    private String titleRule;

    @ApiModelProperty(value = "副标题规则")
    private String subTitleRule;

    @ApiModelProperty(value = "发布时间规则")
    private String publishTimeRule;

    @ApiModelProperty(value = "文章摘要规则")
    private String essentialRule;

    @ApiModelProperty(value = "作者规则")
    private String authorRule;

    @ApiModelProperty(value = "正文规则")
    @NotNull
    private String contentRule;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "是否自动采集：0-非自动，1-自动")
    private Boolean isAutomatic;

    @ApiModelProperty(value = "自动采集时间")
    private LocalDateTime autoTime;

    @ApiModelProperty(value = "逻辑删除")
    private Boolean isDeleted;

    @TableField(exist = false)
    private String catalogName;


}
