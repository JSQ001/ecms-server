package com.eicas.cms.component;

import com.eicas.cms.pojo.enumeration.ResultCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
//@ApiModel(value = "返回结果实体类", description = "结果实体类")
public class BaseResponse<T> implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 返回状态码
   * */
  private Integer code;

  /**
   * 响应信息
   * */
  private String message;

  /**
   * 响应数据
   * */
  private Object data;

  public BaseResponse(T data) {
    this(ResultCode.SUCCESS, data);
  }
  public BaseResponse(ResultCode resultCode, T data) {
    this.code = resultCode.getCode();
    this.message = resultCode.getMessage();
    this.data = data;
  }
}
