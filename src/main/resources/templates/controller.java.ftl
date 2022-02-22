package ${package.Controller};

import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;

<#if superControllerClassPackage??>
    import ${superControllerClassPackage};
</#if>
import java.util.List;

/**
* <p>
* ${table.comment} 前端控制器
* </p>
*
* @author ${author}
* @since ${date}
*/
@Api(tags = "${table.comment}")
@ResponseResult()
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
        @ApiImplicitParam(name = "page", value = "页面", dataType = "Long"),
        @ApiImplicitParam(name = "size", value = "页面数据量", dataType = "Long"),
    })
    @GetMapping(value = "/page")
    public Page<${entity}> searchPages(${entity} entity, Page<${entity}> page) {
        return ${table.serviceName?uncap_first}.page(entity,page);
    }

    @ApiOperation(value = "${table.comment}新增/修改")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "body", value = "请求体", paramType="body", dataType = "${entity}.class", required = true),
    })
    @PostMapping(value = "/createOrUpdate")
    public boolean createOrUpdate(@RequestBody ${entity} entity) {
        return ${table.serviceName?uncap_first}.saveOrUpdate(entity);
    }

    @ApiOperation(value = "根据id物理删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id",required = true)})
    @DeleteMapping("/delete/{id}")
    public boolean physicalDelete(@PathVariable(value="id") String id){
        return ${table.serviceName?uncap_first}.removeById(id);
    }

    @ApiOperation(value = "根据id物理删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id",required = true)})
    @PostMapping("/delete/{id}")
    public boolean logicalDelete(@PathVariable(value="id") String id){
        return ${table.serviceName?uncap_first}.update(new UpdateWrapper<${entity}>().eq("id",id).set("deleted", 1));
    }

   <#-- @ApiOperation(value = "${table.comment}详情", response = ${entity}.class)
    @GetMapping(value = "/info/{id}")
    public  Object info(@PathVariable Long id) {

    Object data = ${table.serviceName?uncap_first}.info(id);
    return RetJson.ok(data);
    }



    @ApiOperation(value = "${table.comment}修改")
    @PostMapping(value = "/modify")
    public  Object modify(@RequestBody ${entity} param) {

    ${table.serviceName?uncap_first}.modify(param);
    return RetJson.ok();
    }

    @ApiOperation(value = "${table.comment}删除(单个条目)")
    @GetMapping(value = "/remove/{id}")
    public  Object remove(@PathVariable Long id) {

    ${table.serviceName?uncap_first}.remove(id);
    return RetJson.ok();
    }

    @ApiOperation(value = "${table.comment}删除(多个条目)")
    @PostMapping(value = "/removes")
    public  Object removes(@RequestBody List<Long> ids) {

    ${table.serviceName?uncap_first}.removes(ids);
    return RetJson.ok();
    }
-->
}
</#if>