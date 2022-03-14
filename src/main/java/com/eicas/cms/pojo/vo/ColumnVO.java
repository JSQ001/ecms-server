package com.eicas.cms.pojo.vo;

import com.eicas.cms.pojo.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ColumnVO extends BaseVO {

    @ApiModelProperty("栏目名称")
    private String name;

    @ApiModelProperty("开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("结束时间")
    private LocalDateTime endTime;

}
