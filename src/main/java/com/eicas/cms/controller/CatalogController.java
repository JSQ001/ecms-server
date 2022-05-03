package com.eicas.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.entity.ArticleEntity;
import com.eicas.cms.entity.CatalogEntity;
import com.eicas.cms.pojo.param.CatalogArticleStaticsResult;
import com.eicas.cms.pojo.param.CatalogQueryParam;
import com.eicas.cms.service.ICatalogService;
import com.eicas.common.ResultData;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 文章栏目前端控制
 *
 * @author osnudt
 * @since 2022-04-19
 */
@RestController
@RequestMapping("/api/catalog")
public class CatalogController {

    @Resource
    ICatalogService catalogService;

    /**
     * 根据ID查询单个栏目信息
     */
    @GetMapping(value = "/{id}")
    public CatalogEntity getCatalogById(@NotNull @PathVariable(value = "id") Long id) {
        return catalogService.getById(id);
    }

    /**
     * 根据code查询单个栏目信息
     */
    @ApiOperation(value="根据栏目code查询栏目详情",response = ArticleEntity.class)
    @ApiImplicitParam(name = "栏目code", value = "code", required = true, dataTypeClass = String.class)
    @GetMapping("/getDetailByCode")
    public CatalogEntity getCatalogByCode(@RequestParam(required = true) String code) {
        return catalogService.getByCode(code);
    }

    /**
     * 根据栏目ID查询下属子栏目
     * @param id 栏目ID
     * @param current 当前页
     * @param size 页信息条数
     * @return 分页数据
     */
    @GetMapping(value = "/child")
    public Page<CatalogEntity> listChildCatalogById(@NotNull @RequestParam(value = "id") Long id,
                                                    @RequestParam(value = "current", defaultValue = "1") Integer current,
                                                    @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return catalogService.listChildCatalogById(id, current, size);
    }

    /**
     * 根据code查询下级栏目
     * @param code 栏目code
     * @return 子栏目list
     */
    @ApiOperation(value="根据code查询下级栏目",response = List.class)
    @ApiImplicitParam(name = "栏目code", value = "code", required = true, dataTypeClass = String.class)
    @GetMapping(value = "/childrenWithCode")
    public List<CatalogEntity> listChildCatalogById(@NotNull @RequestParam(value = "code") String code) {
        return catalogService.listChildrenWithCode(code);
    }

    /**
     * 根据栏目父栏目ID查询下属子栏目树
     * @param parentId 父栏目ID
     * @return list
     */
    @GetMapping(value = "/tree/parentId")
    public List<CatalogEntity> listChildCatalogById(Long parentId) {
        return catalogService.listCatalogTreeByParentId(parentId);
    }

    /**
     * 将指定栏目移动至目标栏目(parentId)下，成为其子栏目，顶级栏目ID为0
     * @param id 指定栏目ID
     * @param parentId 目标父栏目
     * @return 是否成功
     */
    @PostMapping(value = "/move")
    public ResultData moveCatalog(@NotNull @RequestParam(value = "id") Long id,
                               @NotNull @RequestParam(value = "parentId") Long parentId) {
        if(Objects.equals(id, parentId)){
            return ResultData.failed("不能设置自身为父栏目");
        }
        if(!catalogService.moveCatalog(id,parentId)){
            return ResultData.failed("保存失败！");
        }
        return ResultData.success("保存成功！");
    }
    /**
     * 复制指定ID栏目为新栏目
     * @param id 指定栏目ID
     * @return 新栏目ID
     */
    @PostMapping(value = "/copy")
    public CatalogEntity copyCatalog(@NotNull @RequestParam(value = "id") Long id) {
        return catalogService.copyCatalog(id);
    }

    /**
     * 根据参数查询栏目，返回栏目分页数据
     */
    @GetMapping(value = "/list")
    public Page<CatalogEntity> listCatalog(CatalogQueryParam param,
                                           @RequestParam(value = "current", defaultValue = "1") Integer current,
                                           @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return catalogService.listCatalog(param, current, size);
    }

    @PostMapping(value = "/create")
    public Boolean createCatalog(@Valid @RequestBody CatalogEntity entity) {
        return catalogService.createCatalog(entity);
    }

    @PostMapping("/delete/{id}")
    public ResultData deleteCatalogById(@PathVariable(value = "id") Long id) {
        return catalogService.removeById(id);
    }

    @PostMapping("/delete")
    public Boolean batchDelete(@RequestBody List<Long> ids) {
        return catalogService.removeBatchByIds(ids);
    }

    @PostMapping("/update")
    public ResultData updateCatalog(@Valid @RequestBody CatalogEntity entity) {
        entity.setCode(null);
        if(Objects.equals(entity.getParentId(), entity.getId())){
            return ResultData.failed("不能设置自身为父栏目");
        }
        if(!catalogService.updateById(entity)){
            return ResultData.failed("保存失败！");
        }
        return ResultData.success("保存成功！");
    }
}
