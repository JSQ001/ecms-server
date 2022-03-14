package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import javax.annotation.Resource;


/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
*/
@Service
@Transactional
@Slf4j
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {
    @Resource
    private ${entity}Mapper ${'${entity}'?uncap_first}Mapper;
    /**
     * 根据传入entity属性值分页查询
     * @param param
     * @param page
     * @return Page<${entity}>
     */
    @Override
    public Page<${entity}> page(${entity} param, Page<${entity}> page) {
        LambdaQueryWrapper<${entity}> lambdaQueryWrapper = new QueryWrapper<${entity}>().lambda();
<#--     <#list table.fields as field>-->
<#--&lt;#&ndash;            // ${field.comment}&ndash;&gt;-->
<#--        <#if !entityLombokModel>-->
<#--        <#if field.propertyType == "Boolean">-->
<#--        <#assign getprefix="is"/>-->
<#--        <#else>-->
<#--        <#assign getprefix="get"/>-->
<#--        </#if>-->
<#--        <#if field.propertyType == "String">-->
<#--        lambdaQueryWrapper.eq(StringUtils.hasText(param.${getprefix}${field.capitalName}()), ${entity}::${getprefix}${field.capitalName}, param.${getprefix}${field.capitalName}());-->
<#--        <#else>-->
<#--        lambdaQueryWrapper.eq(param.${getprefix}${field.capitalName}() != null, ${entity}::${getprefix}${field.capitalName}, param.${getprefix}${field.capitalName}());-->
<#--        </#if>-->
<#--        <#else>-->
<#--        <#if field.propertyType == "String">-->
<#--        lambdaQueryWrapper.eq(StringUtils.hasText(param.get${field.capitalName}()), ${entity}::get${field.capitalName}, param.get${field.capitalName}());-->
<#--        <#else>-->
<#--        <#if field.capitalName == "isDeleted">-->
<#--        lambdaQueryWrapper.eq(${entity}::get${field.capitalName}, param.get${field.capitalName}() != null ? param.get${field.capitalName}() : 0);-->
<#--        <#else>-->
<#--        lambdaQueryWrapper.eq(param.get${field.capitalName}() != null, ${entity}::get${field.capitalName}, param.get${field.capitalName}());-->
<#--        </#if>-->
<#--        </#if>-->
<#--        </#if>-->
<#--        </#list>-->

        return page(page, lambdaQueryWrapper);
    }

    /**
    * 保存或更新
    * @param entity
    * @return
    */
    @Override
    public boolean createOrUpdate(${entity} entity){
        return saveOrUpdate(entity);
    }

    /**
    * 根据id逻辑删除
    * @param id
    * @return
    */
    @Override
    public boolean logicalDeleteById(Long id){
        return removeById(id);
    }
}
</#if>
