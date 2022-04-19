package com.eicas.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.pojo.entity.MemorialEntity;
import com.eicas.cms.pojo.param.MemorialQueryParam;
import com.eicas.cms.service.IMemorialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 大事记控制器
 *
 * @author osnudt
 * @since 2022-04-18
 */
@Api(tags = "大事件接口")
@RestController
@RequestMapping("/api/memorials")
public class MemorialController {

    @Resource
    IMemorialService memorialService;

    /**
     * 根据ID查询1条大事记条目
     */
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "根据id获取大事记详情")
    public MemorialEntity getMemorial(@PathVariable(value = "id") Long id) {
        return memorialService.getById(id);
    }

    /**
     * 查询大事记条目
     */
    @GetMapping(value = "/list")
    @ApiOperation(value = "条件查询大事记（分页）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "页码", required = true, defaultValue = "1", dataType = "int", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "size", value = "页码大小", required = true, defaultValue = "10", dataType = "int", dataTypeClass = Integer.class)
    })
    public Page<MemorialEntity> listMemorial(@Valid MemorialQueryParam param,
                                             @RequestParam(value = "current", defaultValue = "1") Integer current,
                                             @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return memorialService.listMemorial(param,current,size);
    }

    @PostMapping(value = "/create")
    @ApiOperation(value = "新增大事件")
    public Boolean createMemorial(@Valid @RequestBody MemorialEntity entity) {
        return memorialService.save(entity);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "根据id逻辑删除大事记")
    public Boolean deleteMemorial(@PathVariable(value = "id") Long id) {
        return memorialService.removeById(id);
    }

    @PostMapping("/batchDel")
    @ApiOperation(value = "批量逻辑删除大事记")
    public Boolean batchDelete(@RequestParam("ids") List<Long> ids) {
        return memorialService.removeBatchByIds(ids);
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新大事记")
    public Boolean updateMemorial(@Valid @RequestBody MemorialEntity entity) {
        return memorialService.updateById(entity);
    }
}
