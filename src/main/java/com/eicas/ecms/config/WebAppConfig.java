package com.eicas.ecms.config;

import com.eicas.ecms.interceptor.ResponseResultInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {


    @Value("${file.path}")
    private String filePath;

    @Value("${file.imagePath}")
    private String fileImagePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * 访问地址：http://localhost:8080/images时,会映射到uploadPath目录
         *
         */
        registry.addResourceHandler("/download/*/**")
                .addResourceLocations("file:" + filePath);
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler(fileImagePath).addResourceLocations("file:" + filePath);
    }


    // SpringMVC 需要手动添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册自定义拦截器
        ResponseResultInterceptor interceptor = new ResponseResultInterceptor();
        registry.addInterceptor(interceptor);
        //  .addPathPatterns("")
        //  .excludePathPatterns()
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //对那些请求路径进行跨域处理
        registry.addMapping("/api/**")
                //允许的请求头，默认允许所有的请求头
                .allowedHeaders("*")
                //允许的方法，默认允许GET、POST、HEAD
                .allowedMethods("*")
                //探测请求有效时间，单位秒
                .maxAge(1800);
    }
}
