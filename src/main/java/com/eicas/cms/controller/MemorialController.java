package com.eicas.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.pojo.entity.MemorialEntity;
import com.eicas.cms.pojo.param.MemorialQueryParam;
import com.eicas.cms.service.IMemorialService;
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
@RestController
@RequestMapping("/api/memorials")
public class MemorialController {

    @Resource
    IMemorialService memorialService;

    /**
     * 根据ID查询1条大事记条目
     */
    @GetMapping(value = "/{id}")
    public MemorialEntity getMemorial(@PathVariable(value = "id") Long id) {
        return memorialService.getById(id);
    }

    /**
     * 查询大事记条目
     */
    @PostMapping(value = "/list")
    public Page<MemorialEntity> listMemorial(@Valid @RequestBody MemorialQueryParam param,
                                             @RequestParam(value = "current", defaultValue = "5") Integer current,
                                             @RequestParam(value = "size", defaultValue = "1") Integer size) {
        return memorialService.listMemorial(param,current,size);
    }

    @PostMapping(value = "/create")
    public Boolean createMemorial(@Valid @RequestBody MemorialEntity entity) {
        return memorialService.save(entity);
    }

    @PostMapping("/delete/{id}")
    public Boolean deleteMemorial(@PathVariable(value = "id") Long id) {
        return memorialService.removeById(id);
    }

    @PostMapping("/delete")
    public Boolean batchDelete(@RequestParam("ids") List<Long> ids) {
        return memorialService.removeBatchByIds(ids);
    }

    @PostMapping("/update")
    public Boolean updateMemorial(@Valid @RequestBody MemorialEntity entity) {
        return memorialService.updateById(entity);
    }
}
