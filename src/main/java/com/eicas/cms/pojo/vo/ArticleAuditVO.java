package com.eicas.cms.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ArticleAuditVO {

    @ApiModelProperty("文章id")
    private List<Long> auditIds;

    @ApiModelProperty("审核批注")
    private String auditComments;

    @ApiModelProperty("审核人")
    private Long auditUserId;

    @ApiModelProperty("审核人")
    private LocalDateTime auditTime;

    @ApiModelProperty("审核状态：2：通过， 3：不通过")
    private Integer state;

}
