package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
*/
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    /**
     * ${table.comment!}分页列表
     * @param param 根据需要进行传值
     * @return
     */
    @Override
    public Page<${entity}> page(${entity} param, Page<${entity}> page) {

        QueryWrapper<${entity}> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
        <#list table.fields as field>
            // ${field.comment}
            <#if !entityLombokModel>
                <#if field.propertyType == "Boolean">
                    <#assign getprefix="is"/>
                <#else>
                    <#assign getprefix="get"/>
                </#if>
                <#if field.propertyType == "String">
                    .eq(StringUtils.hasText(param.${getprefix}${field.capitalName}()), ${entity}::${getprefix}${field.capitalName}, param.${getprefix}${field.capitalName}())
                <#else>
                    .eq(param.${getprefix}${field.capitalName}() != null, ${entity}::${getprefix}${field.capitalName}, param.${getprefix}${field.capitalName}())
                </#if>
            <#else>
                <#if field.propertyType == "String">
                    .eq(StringUtils.hasText(param.get${field.capitalName}()), ${entity}::get${field.capitalName}, param.get${field.capitalName}())
                <#else>
                    <#if field.capitalName == "deleted">
                    .eq(${entity}::get${field.capitalName}, param.get${field.capitalName}() != null ? param.get${field.capitalName}() : 0)
                    <#else>
                    .eq(param.get${field.capitalName}() != null, ${entity}::get${field.capitalName}, param.get${field.capitalName}())
                    </#if>
                </#if>
            </#if>
        </#list>;

        return page(page, queryWrapper);
    }

    @Override
    public ${entity} info(Long id) {
    return null;
    }

    @Override
    public void add(${entity} param) {

    }

    @Override
    public void modify(${entity} param) {

    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void removes(List<Long> ids) {

    }
}
</#if>
