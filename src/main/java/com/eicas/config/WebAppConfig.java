package com.eicas.config;

import com.eicas.common.interceptor.FormatContentTypeInterceptor;
import com.eicas.common.interceptor.IsLoginInterceptor;
import com.eicas.utils.CommonUtils;
import com.eicas.utils.FileUtils;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Value("${ecms.store-root-path}")
    private String storeRootPath;

    @Value("${ecms.mapping-root-path}")
    private String imageMapping;

    /**
     * get请求，时间类型转换
     */
    private String localDateTimePattern = "yyyy-MM-dd HH:mm:ss";

    @Resource
    private IsLoginInterceptor isLoginInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(CommonUtils.trimUrl(imageMapping) + "/**")
                .addResourceLocations("file:///" + FileUtils.trimSeparator(storeRootPath));
    }

    /**
     * SpringMVC 需要手动添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(isLoginInterceptor);
        registry.addInterceptor(new FormatContentTypeInterceptor());
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //对那些请求路径进行跨域处理
        registry.addMapping("/**")
                .allowedOrigins("*")
                //允许的请求头，默认允许所有的请求头
                .allowedHeaders("*")
                //允许的方法，默认允许GET、POST、HEAD
                .allowedMethods("*")
                //探测请求有效时间，单位秒
                .maxAge(1800);
    }

    /**
     * 开启全局默认类型,全局配置序列化返回 JSON 处理
     * 此方式可以灵活配置任意类型的序列化反序列化
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer builderCustomizer() {
        return builder -> {
            builder.serializerByType(Long.class, ToStringSerializer.instance);
            builder.serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(localDateTimePattern)));
            builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(localDateTimePattern)));
        };
    }
}
