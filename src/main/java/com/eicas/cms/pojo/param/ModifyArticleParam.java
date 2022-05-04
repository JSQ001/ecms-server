package com.eicas.cms.pojo.param;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ModifyArticleParam {
    private Long id;

    private Boolean visible;

    private Boolean top;

    private Boolean recommended;

    private Boolean focus;
}
