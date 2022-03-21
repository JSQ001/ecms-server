package com.eicas.cms.pojo.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("栏目id")
    private Long columnId;

    @ApiModelProperty("文章状态：1-待审核，2-审核通过，3-审核不通过")
    private Integer state;

    @ApiModelProperty("开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty("文章标题")
    private String title;

    @ApiModelProperty("焦点新闻")
    private Boolean isFocus;

    @ApiModelProperty("置顶")
    private Boolean isTop;

}
