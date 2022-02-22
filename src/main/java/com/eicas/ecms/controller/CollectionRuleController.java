package com.eicas.ecms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.ecms.annotation.ResponseResult;
import com.eicas.ecms.entity.CollectionRule;
import com.eicas.ecms.entity.CronCrawl;
import com.eicas.ecms.service.ICollectionRuleService;
import com.eicas.ecms.service.ICronCrawlService;
import com.eicas.ecms.service.QuartzService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * <p>
 * 采集规则表 前端控制器
 * </p>
 *
 * @author jsq
 * @since 2021-11-09
 */
@Api(tags = "采集规则表")
@ResponseResult()
@RestController
@RequestMapping("/api/collection-rule")
public class CollectionRuleController {

    @Resource
    private ICollectionRuleService iCollectionRuleService;
    @Resource
    private ICronCrawlService iCronCrawlService;
    @Resource
    private QuartzService quartzService;


    @ApiOperation(value = "采集规则表分页列表", response = CollectionRule.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "body", value = "请求体", paramType = "body", dataType = "CollectionRule.class", required = true),
            @ApiImplicitParam(name = "page", value = "页面", dataType = "Long"),
            @ApiImplicitParam(name = "size", value = "页面数据量", dataType = "Long"),
    })
    @GetMapping(value = "/page")
    public Page<CollectionRule> searchPages(CollectionRule entity, Page<CollectionRule> page) {
        return iCollectionRuleService.transforCollectionBoPage(entity, page);
    }

    @ApiOperation(value = "采集规则表新增/修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "body", value = "请求体", paramType = "body", dataType = "CollectionRule.class", required = true),
    })
    @PostMapping(value = "/createOrUpdate")
    @Transactional
    public boolean createOrUpdate(@RequestBody CollectionRule entity) {
        if (entity.getAutoCollect() == null) {
            return false;
        }
        if (entity.getAutoCollect() && entity.getCron() == null) {
            return false;
        }
//        CollectionRule collectionRule = iCollectionRuleService.transforRule(entity);
        entity.setRemarks(entity.getAbstractRule());
        iCollectionRuleService.saveOrUpdate(entity);
        String id = entity.getId();
        CronCrawl cronCrawl = new CronCrawl();
        cronCrawl.setRuleId(id);
        cronCrawl.setJName(id);
        cronCrawl.setJGroup(entity.getColumnId());
        cronCrawl.setTName(id);
        cronCrawl.setTGroup(entity.getColumnId());
        if (entity.getAutoCollect()) {
            cronCrawl.setIsDeleted(0);
        } else {
            cronCrawl.setIsDeleted(1);
        }
        //分割
        if (entity.getCron() != null) {
            String hour = entity.getCron().split(":")[0];
            String minute = entity.getCron().split(":")[1];
            String seconds = entity.getCron().split(":")[2];
            String crons = seconds + " " + minute + " " + hour + " * * ?";
            cronCrawl.setCron(crons);
            //将任务进库
            CronCrawl one = iCronCrawlService.getOne(new QueryWrapper<CronCrawl>().eq("rule_id", id));
            if (one == null) {
                //没有就添加，有就更新
                if (entity.getAutoCollect()) {
                    quartzService.addJob(id, entity.getColumnId(), id, entity.getColumnId(), crons, id);
                    return iCronCrawlService.saveOrUpdate(cronCrawl, new UpdateWrapper<CronCrawl>().set("cron", crons).eq("rule_id", id));
                } else {

                    return iCronCrawlService.save(cronCrawl);
//                        return iCronCrawlService.saveOrUpdate(cronCrawl, new UpdateWrapper<CronCrawl>().set("cron", crons).set("is_deleted", 1).eq("rule_id", id));
                }
            } else {
                if (one.getIsDeleted() == 0) {
                    if (entity.getAutoCollect()) {
                        //原来有数据，修改数据，并且在数据库中修改数据
                        quartzService.refreshTask(id, entity.getColumnId(), crons);
                        return iCronCrawlService.saveOrUpdate(new CronCrawl(), new UpdateWrapper<CronCrawl>().set("cron", crons).set("is_deleted", 0).eq("rule_id", id));
                    } else {
                        quartzService.deleteJob(id, entity.getColumnId());
                        return iCronCrawlService.saveOrUpdate(new CronCrawl(), new UpdateWrapper<CronCrawl>().set("cron", crons).set("is_deleted", 1).eq("rule_id", id));
                    }
                } else {
                    if (entity.getAutoCollect()) {
                        quartzService.addJob(id, entity.getColumnId(), id, entity.getColumnId(), crons, id);
                        return iCronCrawlService.saveOrUpdate(cronCrawl, new UpdateWrapper<CronCrawl>().set("cron", crons).set("is_deleted", 0).eq("rule_id", id));
                    } else {
                        //本来就是删除状态，又不需要自动采集
                        return iCronCrawlService.saveOrUpdate(cronCrawl, new UpdateWrapper<CronCrawl>().set("cron", crons).set("is_deleted", 1).eq("rule_id", id));
                    }
                }
            }
            //最后更新数据
        } else {
            CronCrawl one = iCronCrawlService.getOne(new QueryWrapper<CronCrawl>().eq("rule_id", id).isNotNull("cron"));
            if (one != null) {
                //暂停任务
                quartzService.deleteJob(id, entity.getColumnId());
                //修改状态,这里其实不应该存放到数据库中
                return iCronCrawlService.saveOrUpdate(cronCrawl, new UpdateWrapper<CronCrawl>().set("is_deleted", 1).eq("rule_id", id));
            }
            return true;
        }


    }

    @ApiOperation(value = "根据id物理删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id", required = true)})
    @DeleteMapping("/delete/{id}")
    public boolean physicalDelete(@PathVariable(value = "id") String id) {
        return iCollectionRuleService.removeById(id);
    }

    @ApiOperation(value = "根据id逻辑删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id", required = true)})
    @PostMapping("/delete/{id}")
    public boolean logicalDelete(@PathVariable(value = "id") String id) {
        CronCrawl one = iCronCrawlService.getOne(new QueryWrapper<CronCrawl>().eq("rule_id", id).isNotNull("cron"));
        //1、删除任务
        quartzService.deleteJob(id, one.getTGroup());
        //2、将数据库中的数据设置为1
        iCronCrawlService.saveOrUpdate(new CronCrawl(), new UpdateWrapper<CronCrawl>().set("is_deleted", 1).eq("rule_id", id));
        return iCollectionRuleService.update(new UpdateWrapper<CollectionRule>().eq("id", id).set("is_deleted", 1));
    }

    @ApiOperation(value = "根据id返回特定采集规则")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id", required = true)})
    @GetMapping("/rules/{id}")
    public CollectionRule showIndex(@PathVariable(value = "id") String id) {
        CollectionRule c = iCollectionRuleService.getOne(new QueryWrapper<CollectionRule>().eq("id", id));
        return iCollectionRuleService.transforRuleDto(c);
    }
}
