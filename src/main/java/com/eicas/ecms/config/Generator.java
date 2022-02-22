package com.eicas.ecms.config;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class Generator {
    private static final String dataSourceUrl = "jdbc:mysql://localhost:3306/ecms_server?useUnicode=true&useSSL=false&characterEncoding=utf8&allowPublicKeyRetrieval=true";
    private static final String username = "root";
    private static final String password = "123456";
    /**
     * controller输出模板
     */
    private static final String CONTROLLER_TEMPLATE = "templates/freemarker/controller.java.ftl";
    @Value("${server.port}")
    private Integer port;

    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");

        FastAutoGenerator.create(dataSourceUrl, username, password)
                .globalConfig(builder -> {
                    builder.author("zyt") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .disableOpenDir()
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(projectPath + "/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {

                    builder.parent("com.eicas") // 设置父包名
                            .moduleName("ecms")      // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, projectPath + "/src/main/resources/mapper/")); // 设置mapperXml生成路径

                })
                .strategyConfig(builder -> {
                    builder.addInclude("info_cron_crawl") // 设置需要生成的表名
                            .addTablePrefix("info_")  // 设置过滤表前缀
                            .entityBuilder()
                            .enableTableFieldAnnotation()
                            .idType(IdType.ASSIGN_ID)
                            .enableRemoveIsPrefix()
                            .addTableFills(
                                    //自动填充
                                    new Column("created_time", FieldFill.INSERT),
                                    new Column("created_by", FieldFill.INSERT),
                                    new Column("is_deleted", FieldFill.INSERT),
                                    new Column("updated_time", FieldFill.INSERT_UPDATE),
                                    new Column("updated_by", FieldFill.INSERT_UPDATE)
                            )
                            .enableLombok()
                            .controllerBuilder().enableRestStyle();
                })
                .templateConfig(builder -> {
                    builder
                            .service("/templates/service.java")
                            .controller("templates/controller.java");

                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    public void test() {
        System.out.println(port);
    }
}
