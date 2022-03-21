package com.eicas.cms.handler;

import com.eicas.cms.exception.APIException;
import com.eicas.cms.component.BaseResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Type;


/**
 *
 * 统一处理返回值/响应体
 */
@RestControllerAdvice
@Slf4j
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> aClass) {
        // 如果接口返回的类型本身就是ResultVO那就没有必要进行额外的操作，返回false
        if(returnType.getDeclaringClass().getName().contains("springfox")){
            return false;
        }
        return !returnType.getGenericParameterType().equals(BaseResponse.class);

    }

   // 对返回值做包装处理，如果属于异常结果，则需要再包装
   @Override
   public Object beforeBodyWrite(Object data, MethodParameter returnType, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
       // String类型不能直接包装，所以要进行些特别的处理
       // springfox.documentation.swagger.web.SecurityConfiguration
       if (returnType.getGenericParameterType().equals(String.class)) {
           ObjectMapper objectMapper = new ObjectMapper();
           try {
               // 将数据包装在BaseResponse里后，再转换为json字符串响应给前端
               return objectMapper.writeValueAsString(new BaseResponse<>(data));
           } catch (JsonProcessingException e) {
               throw new APIException();
           }
       }

       serverHttpResponse.setStatusCode(HttpStatus.OK);
       return data instanceof BaseResponse ? data : new BaseResponse<>(data);
   }
}
