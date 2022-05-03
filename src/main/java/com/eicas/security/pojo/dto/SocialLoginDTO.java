package com.eicas.security.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.UUID;

/**
 * @author osnudt
 * @since 2022/5/1
 */

@Data
public class SocialLoginDTO {

    @ApiModelProperty(value = "统一认证中心用户ID")
    Long uuid;
    @ApiModelProperty(value = "用户账号，对应usercode")
    String username;
    @ApiModelProperty(value = "用户名")
    String realname;
    @ApiModelProperty(value = "用户角色")
    String roles;
    @ApiModelProperty(value = "访问令牌")
    String accessToken;
    @ApiModelProperty(value = "应用ID")
    String appId;
    @ApiModelProperty(value = "应用类型")
    String appType;
    @ApiModelProperty(value = "令牌类型")
    String tokenType;
    @ApiModelProperty(value = "刷新令牌")
    String refreshToken;
    @ApiModelProperty(value = "令牌过期时间")
    Long expired;
    @ApiModelProperty(value = "令牌作用域")
    String scope;
    @ApiModelProperty(value = "JTI编码")
    UUID jti;
}
