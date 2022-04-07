package com.eicas.cms.handler;

import com.eicas.cms.exception.APIException;
import com.eicas.cms.component.BaseResponse;
import com.eicas.cms.exception.BusinessException;
import com.eicas.cms.pojo.enumeration.ResultCode;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * 全局异常处理类
 * @RestControllerAdvice(@ControllerAdvice)，拦截异常并统一处理
 *
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * 自定义异常APIException
   */
  @ExceptionHandler(APIException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public BaseResponse<Object> APIExceptionHandler(APIException e) {
    log.error("api异常！");
    return new BaseResponse<>(ResultCode.FAILED, e.getMessage());
  }

  @ExceptionHandler(BusinessException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public BaseResponse<Object> BusinessExceptionHandler(BusinessException e) {
    log.error(e.getResultCode().getMessage());
    return new BaseResponse<>(e.getResultCode(),null);
  }

  /**
   * 方法参数错误异常
   * @param e
   * @return
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public BaseResponse<Object> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
    log.error("方法参数错误异常！");
    List<String> list=new ArrayList<>();
    // 从异常对象中拿到ObjectError对象
    if (!e.getBindingResult().getAllErrors().isEmpty()){
      for(ObjectError error:e.getBindingResult().getAllErrors()){
        list.add(error.getDefaultMessage().toString());
      }
    }
    // 然后提取错误提示信息进行返回
    return new BaseResponse<>(ResultCode.VALIDATE_FAILED, list);
  }

  // 参数校验异常
  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public BaseResponse<Object> httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
    log.error("请求数据媒体格式错误！");
    return new BaseResponse<>(ResultCode.FAILED, e.getMessage());
  }
  // 数据格式转换异常
  @ExceptionHandler(InvalidFormatException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public BaseResponse<Object> invalidFormatException(InvalidFormatException e) {
    log.error("数据转换异常！" + e.getMessage());
    return new BaseResponse<>(ResultCode.FAILED, e.getMessage());
  }


  @ExceptionHandler(MysqlDataTruncation.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public BaseResponse<Object> mysqlDataTruncation(MysqlDataTruncation e) {
    log.error("数据转换异常！" + e.getMessage());
    return new BaseResponse<>(ResultCode.Sql_ERROR, e.getMessage());
  }






}
