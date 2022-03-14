package com.eicas.cms.controller;

import com.eicas.cms.pojo.entity.Article;
import com.eicas.cms.pojo.vo.ArticleAuditVO;
import com.eicas.cms.pojo.vo.ArticleVO;
import com.eicas.cms.pojo.vo.ArticleStatisticalResults;
import com.eicas.cms.service.IArticleService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;


/**
* 文章信息表 前端控制器
* @author jsq
* @since 2022-03-05
*/
@Api(tags = "文章信息表")
@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Resource
    private IArticleService iArticleService;

    @ApiOperation(value = "文章信息表分页列表", response = Article.class)
    @GetMapping(value = "/list")
    public Page<Article> listArticles(ArticleVO articleQueryVo) {
        return iArticleService.listArticles(articleQueryVo);
    }

    @ApiOperation(value = "根据id查询文章信息", response = Article.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "文章id",required = true,  dataTypeClass = Long.class)})
    @GetMapping(value = "/query")
    public Article queryArticleById(Long id) {
        return iArticleService.getById(id);
    }

    @ApiOperation(value = "统计全部文章信息", response = Article.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间", dataTypeClass = LocalDateTime.class),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataTypeClass = LocalDateTime.class)
    })
    @GetMapping(value = "/statistics")
    public ArticleStatisticalResults getStatistics(LocalDateTime startTime, LocalDateTime endTime) {
         return iArticleService.getStatistics(startTime, endTime);
    }

    @ApiOperation(value = "统计指定栏目的子栏目文章信息", response = Article.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "栏目code", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "startTime", value = "开始时间", dataTypeClass = LocalDateTime.class),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataTypeClass = LocalDateTime.class)
    })
    @GetMapping(value = "/statisticsByColumn")
    public List<ArticleStatisticalResults> getStatisticByColumn(String code,LocalDateTime startTime, LocalDateTime endTime) {
        return iArticleService.getStatisticByColumn(code, startTime, endTime);
    }

    @ApiOperation(value = "文章信息表新增/修改")
    @PostMapping(value = "/createOrUpdate")
    public Boolean createOrUpdate(@Valid @RequestBody Article entity) {
        return iArticleService.createOrUpdate(entity);
    }

    @ApiOperation(value = "根据id删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "文章id", required = true, dataTypeClass = Long.class)})
    @PostMapping("/delete/{id}")
    public Boolean logicalDelete(@PathVariable(value="id") Long id){
        return iArticleService.logicalDeleteById(id);
    }

    @ApiOperation(value = "批量删除文章")
    @ApiImplicitParams({@ApiImplicitParam(name = "ids", value = "删除id列表",required = true, dataTypeClass = Long.class)})
    @PostMapping("/batchDel")
    public Boolean batchDel(@RequestBody List<Long> ids){
        return iArticleService.batchDel(ids);
    }

    @ApiOperation(value = "审核文章")
    @PostMapping("/audit")
    public Boolean auditArticle(@RequestBody List<ArticleAuditVO> articleList){
        return iArticleService.auditArticle(articleList);
    }

}
