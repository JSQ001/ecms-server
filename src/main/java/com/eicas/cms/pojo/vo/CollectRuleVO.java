package com.eicas.cms.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CollectRuleVO extends BaseVO{
    @ApiModelProperty("规则名称")
    private String name;
}
