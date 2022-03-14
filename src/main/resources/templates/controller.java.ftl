package ${package.Controller};

import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.annotation.Resource;
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
* ${table.comment} 前端控制器
* @author ${author}
* @since ${date}
*/
@Api(tags = "${table.comment}")
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("/api/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Resource
    private ${table.serviceName} ${table.serviceName?uncap_first};

    @ApiOperation(value = "${table.comment}分页列表", response = ${entity}.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "body", value = "请求体", paramType="body", dataType = "${entity}.class", required = true),
        @ApiImplicitParam(name = "page", value = "页面", dataType = "int"),
        @ApiImplicitParam(name = "size", value = "页面数据量", dataType = "int"),
    })
    @GetMapping(value = "/list")
    public Page<${entity}> list${entity}s(${entity} entity, Page<${entity}> page) {
        return ${table.serviceName?uncap_first}.page(entity,page);
    }

    @ApiOperation(value = "根据id查询${table.comment}", response = ${entity}.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "id", required = true),})
    @GetMapping(value = "/query")
    public ${entity} query${entity}ById(Long id) {
    return iArticleService.getById(id);
    }

    @ApiOperation(value = "${table.comment}新增/修改")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "body", value = "请求体", paramType="body", dataType = "${entity}.class", required = true),
    })
    @PostMapping(value = "/createOrUpdate")
    public boolean createOrUpdate(@Valid @RequestBody ${entity} entity) {
        return ${table.serviceName?uncap_first}.createOrUpdate(entity);
    }


    @ApiOperation(value = "根据id逻辑删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id",required = true)})
    @PostMapping("/delete/{id}")
    public boolean logicalDelete(@PathVariable(value="id") Long id){
        return ${table.serviceName?uncap_first}.logicalDeleteById(id);
    }
}
</#if>