package com.eicas.cms.pojo.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class ArticleDateVo extends BaseVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("时间参数【天，周，月，年】")
    public int paramDate;

    @ApiModelProperty("时间天数")
    public int paramDateNum;


}
