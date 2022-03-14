package com.eicas.cms.pojo.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 响应状态枚举类
 * */

@AllArgsConstructor
@Getter
public enum ResultCode {

  SUCCESS(200, "成功"),

  FAILED(500, "接口错误"),

  SYSTEM_ERROR(1000, "系统异常，请稍后重试"),

  VALIDATE_FAILED(2000, "参数错误"),

  PARAM_IS_BLANK(1002, "参数为空"),

  PARAM_TYPE_BIND_ERROR(1003, "参数类型错误"),

  PARAM_NOT_COMPLETE(1004, "参数缺失"),

  USER_NOT_LOGIN_IN(2001, "用户未登录"),

  ACCOUNT_NOT_EXIST(2002, "账号不存在");

  private final Integer code;
  private final String message;
}
