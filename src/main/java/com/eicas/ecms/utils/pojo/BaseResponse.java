package com.eicas.ecms.utils.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
//@ApiModel(value = "返回结果实体类", description = "结果实体类")
public class BaseResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  //@ApiModelProperty(value = "返回码")
  private Integer code;

  //@ApiModelProperty(value = "返回消息")
  private String message;

  //@ApiModelProperty(value = "返回数据")
  private Object data;

  private BaseResponse() {

  }

  public BaseResponse(ResultCode resultCode, Object data) {
    this.code = resultCode.code();
    this.message = resultCode.message();
    this.data = data;
  }

  private void setResultCode(ResultCode resultCode) {
    this.code = resultCode.code();
    this.message = resultCode.message();
  }

  // 返回成功
  public static BaseResponse success() {
    BaseResponse result = new BaseResponse();
    result.setResultCode(ResultCode.SUCCESS);
    return result;
  }
  // 返回成功
  public static BaseResponse success(Object data) {
    BaseResponse result = new BaseResponse();
    result.setResultCode(ResultCode.SUCCESS);
    result.setData(data);
    return result;
  }

  // 返回失败
  public static BaseResponse fail(Integer code, String message) {
    BaseResponse result = new BaseResponse();
    result.setCode(code);
    result.setMessage(message);
    return result;
  }
  // 返回失败
  public static BaseResponse fail(ResultCode resultCode) {
    BaseResponse result = new BaseResponse();
    result.setResultCode(resultCode);
    return result;
  }
}
