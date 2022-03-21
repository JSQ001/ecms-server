package com.eicas.cms.exception;

import com.eicas.cms.pojo.enumeration.ResultCode;
import lombok.Getter;


/**
 *  业务异常类
 * */

@Getter
public class BusinessException extends RuntimeException{
    private final ResultCode resultCode;


    public BusinessException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
