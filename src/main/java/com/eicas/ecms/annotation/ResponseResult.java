package com.eicas.ecms.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface ResponseResult {
  /**
   * 值
   */
  Class value() default Object.class;

}
