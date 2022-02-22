package com.eicas.ecms.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eicas.ecms.annotation.ResponseResult;
import com.eicas.ecms.entity.CronCrawl;
import com.eicas.ecms.mapper.CronCrawlMapper;
import com.eicas.ecms.service.ICronCrawlService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.ecms.service.QuartzService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.json.Json;

import java.util.List;

/**
* <p>
*  前端控制器
* </p>
*
* @author zyt
* @since 2022-01-11
*/
@Api(tags = "定时采集表")
@ResponseResult()
@RestController
@RequestMapping("/api/cronCrawl")
public class CronCrawlController {

    @Resource
    private ICronCrawlService iCronCrawlService;
    @Autowired
    private QuartzService quartzService;

    /**
     * 添加任务
     * @param cron
     * @return
     */
    @PostMapping("/insert")
    public Boolean insertTask(CronCrawl cron) {
        quartzService.addJob(cron.getJName(), cron.getJGroup(), cron.getTName(), cron.getTGroup(), cron.getCron(),cron.getRuleId());
        iCronCrawlService.add(cron);
        return true;

    }

    /**
     * 暂停任务
     */
    @GetMapping("/pause")
    public Boolean pauseTask(String ruleId) {
        CronCrawl ruleCraw = iCronCrawlService.getOne(new QueryWrapper<CronCrawl>()
                .eq("rule_id", ruleId));
        quartzService.pauseJob(ruleCraw.getJName(), ruleCraw.getJGroup());
//        return  iCronCrawlService.remove(new QueryWrapper<CronCrawl>().eq("rule_id", ruleId));
        //将该条任务设置为1
    return     iCronCrawlService.update(new UpdateWrapper<CronCrawl>().eq("rule_id", ruleId).set("is_deleted", 1));
    }

    /**
     * 继续任务
     */
    @GetMapping("/resume")
    public Boolean resumeTask(String ruleId) {
        CronCrawl ruleCraw = iCronCrawlService.getOne(new QueryWrapper<CronCrawl>()
                .eq("rule_id", ruleId));
        if (ruleCraw == null) {
            return false;
        }
        quartzService.resumeJob(ruleCraw.getJName(), ruleCraw.getJGroup());
        return true;
    }

    /**
     * 删除任务
     */
    @GetMapping("/delete")
    public Boolean deleteTask(String ruleId) {
        CronCrawl ruleCraw = iCronCrawlService.getOne(new QueryWrapper<CronCrawl>()
                .eq("rule_id", ruleId));
        if (ruleCraw == null) {
            return false;
        }
        quartzService.deleteJob(ruleCraw.getJName(), ruleCraw.getJGroup());
        return true;
    }
}
