package com.eicas.cms.exception;
import com.eicas.cms.pojo.enumeration.ResultCode;
import lombok.Getter;
/**
 * @author jsq
 * @date 2022/03/04 16:43
 * @description: 自定义异常
 */
@Getter
public class APIException extends RuntimeException {
    private int code;
    private String message;
    public APIException() {
        this(ResultCode.FAILED);
    }    public APIException(ResultCode failed) {
        this.code=failed.getCode();
        this.message=failed.getMessage();
    }}
