package com.eicas.ecms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
//@EnableWebMvc
//@ComponentScan("com.eicas.ecms.controller")
public class Swagger3 {

//    @Bean
//    public Docket docket() {
//        return new Docket(DocumentationType.OAS_30).apiInfo(
//                new ApiInfoBuilder()
//                        .contact(new Contact("Kern", "", "825***@qq.com"))
//                        .title("Swagger2测试项目")
//                        .build()
//        );
//    }
}
