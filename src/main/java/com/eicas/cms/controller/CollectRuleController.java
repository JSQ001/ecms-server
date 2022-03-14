package com.eicas.cms.controller;

import com.eicas.cms.component.ScheduleTask;
import com.eicas.cms.pojo.entity.Article;
import com.eicas.cms.pojo.entity.CollectRule;
import com.eicas.cms.pojo.vo.CollectRuleVO;
import com.eicas.cms.service.ICollectRuleService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.annotation.Resource;
import java.time.LocalDateTime;


/**
* <p>
* 采集规则表 前端控制器
* </p>
*
* @author jsq
* @since 2022-03-06
*/
@Api(tags = "采集规则表")
@RestController
@RequestMapping("/api/collect-rule")
@Slf4j
public class CollectRuleController {

    @Resource
    private ICollectRuleService iCollectRuleService;
    @Resource
    private ScheduleTask scheduleTask;
    @ApiOperation(value = "采集规则表分页查询", response = CollectRule.class)
    @GetMapping(value = "/list")
    public Page<CollectRule> listCollectRules(CollectRuleVO collectRuleVO) {
        return iCollectRuleService.listCollectRules(collectRuleVO);
    }

    @ApiOperation(value = "根据id查询文章信息", response = Article.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "id", required = true, dataTypeClass = Long.class)})
    @GetMapping(value = "/query")
    public CollectRule queryCollectRuleById(Long id) {
        return iCollectRuleService.getById(id);
    }

    @ApiOperation(value = "采集规则表新增/修改")
    @PostMapping(value = "/createOrUpdate")
    public boolean createOrUpdate(@Valid @RequestBody CollectRule entity) {
        return iCollectRuleService.createOrUpdate(entity);
    }


    @ApiOperation(value = "根据id删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id",required = true, dataTypeClass = Long.class)})
    @PostMapping("/delete/{id}")
    public boolean logicalDelete(@PathVariable(value="id") Long id){
        return iCollectRuleService.logicalDeleteById(id);
    }

}
