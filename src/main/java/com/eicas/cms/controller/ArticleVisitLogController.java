package com.eicas.cms.controller;


import com.eicas.cms.entity.ArticleEntity;
import com.eicas.cms.entity.ArticleVisitLog;
import com.eicas.cms.pojo.param.DayArticleStaticsResult;
import com.eicas.cms.service.ArticleVisitLogService;
import com.eicas.utils.NetworkUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


/**
 * 文章记录表 前端控制器
 *
 * @author osnudt
 * @since 2022-04-19
 */
@RestController
@RequestMapping("/api/article/visit/log")
public class ArticleVisitLogController {

    @Resource
    ArticleVisitLogService articleVisitLogService;

    @ApiOperation(value="创建文章访问记录",response = ArticleVisitLog.class)
    @PostMapping(value = "/create")
    public boolean create(@RequestBody ArticleVisitLog param, HttpServletRequest request) {
        param.setIp(NetworkUtil.getIpAddress(request));
        param.setVisitTime(LocalDateTime.now());
        return articleVisitLogService.save(param);
    }

    @PostMapping(value = "/statistic")
    public Integer statisticArticleVisit(
            @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return articleVisitLogService.statisticLog(startTime, endTime);
    }

    @PostMapping(value = "/statisticDays")
    public List<DayArticleStaticsResult> staticsArticleByDays(
            @Valid @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate startTime,
            @Valid @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate endTime) {
        return articleVisitLogService.staticsArticleByDays(startTime, endTime);
    }
}
