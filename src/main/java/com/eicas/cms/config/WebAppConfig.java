package com.eicas.cms.config;

import com.eicas.cms.component.ClientAuthenticate;
import com.eicas.cms.interceptor.FormatContentTypeInterceptor;
import com.eicas.cms.interceptor.IsLoginInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Value("${filePath.image}")
    private String imagePath;

    @Value("${filePath.imageMappingPath}")
    private String imageMappingPath;

    /**
    * get请求，时间类型转换
    * */
    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String localDateTimePattern;

    @Resource
    private IsLoginInterceptor isLoginInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * 访问地址：http://localhost:8080/images时,会映射到imagePath目录
         *
         */
        registry.addResourceHandler(imageMappingPath + "/**")
                .addResourceLocations("file:" + imagePath);

        // 配置swagger静态资源映射
//        registry.addResourceHandler("/swagger-ui/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }


    // SpringMVC 需要手动添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册自定义拦截器
        registry.addInterceptor(isLoginInterceptor);
        registry.addInterceptor(new FormatContentTypeInterceptor());
        //  .addPathPatterns("")
        //  .excludePathPatterns()
        WebMvcConfigurer.super.addInterceptors(registry);

        /// 配置swagger拦截器
//        registry.addInterceptor(new SwaggerInterceptor())
//                .addPathPatterns("/**").
//                excludePathPatterns("/swagger-resources/**", "/webjars/**", "/swagger-ui/**", "/v3/**");
//
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

    /**
     * webpack配置类
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }



}
