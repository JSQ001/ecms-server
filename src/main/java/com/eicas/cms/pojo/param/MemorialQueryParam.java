package com.eicas.cms.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author osnudt
 * @since 2022/4/18
 */

@Data
public class MemorialQueryParam {

    @ApiModelProperty("事件开始时间")
    private LocalDateTime beginEventTime;

    @ApiModelProperty("事件结束时间")
    private LocalDateTime endEventTime;

    @ApiModelProperty("发布开始时间")
    private LocalDateTime beginPublishTime;

    @ApiModelProperty("发布截止时间")
    private LocalDateTime endPublishTime;

    @ApiModelProperty("状态：0-草稿,1-已发布")
    private Integer state;

    @ApiModelProperty("通知公告标题（模糊匹配）")
    private String title;
}
