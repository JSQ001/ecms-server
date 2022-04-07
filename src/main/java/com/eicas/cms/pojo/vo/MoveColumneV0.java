package com.eicas.cms.pojo.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoveColumneV0 {

    @ApiModelProperty("源栏目剧id")
    private long sourceId;
    @ApiModelProperty("目标栏目id")
    private long targetId;
    @ApiModelProperty("目标栏目层级编码")
    private String columnCode;





}
