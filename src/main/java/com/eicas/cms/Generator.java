package com.eicas.cms;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.eicas.cms.pojo.entity.BaseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Generator {
    private static final String dataSourceUrl = "jdbc:mysql://localhost:3306/ecms_server?useUnicode=true&useSSL=false&characterEncoding=utf8&allowPublicKeyRetrieval=true";
    private static final String username = "root";
    private static final String password = "jsq123456";
    private static final String CONTROLLER_TEMPLATE = "templates/freemarker/controller.java.ftl";

    public static void main(String[] args) {

        String projectPath = System.getProperty("user.dir");

        FastAutoGenerator.create(dataSourceUrl, username, password)
                // 全局配置
                .globalConfig((scanner, builder) -> builder
                    .author("jsq")
                    .enableSwagger()//开启swagger3
                    .fileOverride()//覆盖文件
                    .disableOpenDir()
                    .commentDate("yyyy-MM-dd")
                    .outputDir(projectPath + "/src/main/java")

                )
                // 包配置
                .packageConfig(builder -> builder
                    .parent("com.test")
                    .entity("pojo.entity")
                    .moduleName("cms")
                )
                // 策略配置
                .strategyConfig((scanner, builder) -> builder
                    .addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
                    // 设置过滤表前缀
                    //.addTablePrefix(scanner.apply("设置过滤表前缀:"))
                    .addTablePrefix("cms")
                    .controllerBuilder()
                        .enableRestStyle()
                    .entityBuilder()
                        .superClass(BaseEntity.class)
                        .enableChainModel()
                        .enableLombok()
                        .enableTableFieldAnnotation()
                        .idType(IdType.ASSIGN_ID)
                        .versionColumnName("version")
                        .versionPropertyName("version")
                        .enableRemoveIsPrefix()
                        .logicDeleteColumnName("is_deleted")
                        .logicDeletePropertyName("deleted")
                        .addSuperEntityColumns("id", "created_by", "created_time", "updated_by", "updated_time")
                        .addTableFills(
                            //自动填充
                            new Column("created_time", FieldFill.INSERT),
                            new Column("created_by", FieldFill.INSERT),
                            new Column("is_deleted", FieldFill.INSERT),
                            new Column("updated_time", FieldFill.INSERT_UPDATE),
                            new Column("updated_by", FieldFill.INSERT_UPDATE)
                        )
                )
                // 模版配置, 默认寻找/templates/***.java
//                .templateConfig(builder -> builder
//                        .service("/templates/service.java")
//                        .controller("templates/controller.java")
//                )


                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }

}
