package com.eicas.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.pojo.entity.Article;
import com.eicas.cms.pojo.vo.ArticleVO;
import com.eicas.cms.pojo.vo.MemorbiliaVo;
import com.eicas.cms.service.IArticleService;
//import com.sun.xml.internal.ws.api.pipe.Tube;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.*;
import  org.springframework.beans.factory.annotation.Value;
import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 大事件信息维护控制器
 * @author jjt
 * @since 2022-04-06
 */
@Api(tags = "大事件信息")
@RestController
@RequestMapping("/api/memorabilia")
public class MemorbiliaController {

    @Resource
    private IArticleService iArticleService;

    @Value("${column.columnId}")
    private  long columnId;



    @ApiOperation(value = "大事件新增/修改")
    @PostMapping(value = "/createOrUpdate")
    public Boolean createOrUpdate(@Valid @RequestBody MemorbiliaVo entity) {

        Article article=new  Article();
        if (entity.getId()!=null&&entity.getId()>0){
            article.setId(entity.getId());

        }
        article.setContent(entity.getContent());
        article.setTitle(entity.getTitle());
        article.setPublishTime(entity.getPublishTime());
        article.setCoverImgUrl(entity.getCoverImgUrl());
        article.setState(1);
        article.setColumnId(this.columnId);
        article.setIsMajor(true);
        return iArticleService.createOrUpdate(article);
    }


    @ApiOperation(value = "大事件信息表分页列表", response = Article.class)
    @GetMapping(value = "/list")
    public Page<Article> listMemorabilias(MemorbiliaVo entity) {
        ArticleVO articleVO=new ArticleVO();
        articleVO.setTitle(entity.getTitle());
        articleVO.setStartTime(entity.getStartTime());
        articleVO.setEndTime(entity.getEndTime());
        articleVO.setIsMajor(Boolean.TRUE);
        articleVO.setYearMonth(entity.getYearMonth());
        articleVO.setCurrent(entity.getCurrent());
        articleVO.setSize(entity.getSize());
        articleVO.setMonth(entity.getMonth());
        articleVO.setYear(entity.getYear());
        return iArticleService.listArticles(articleVO);
    }


    @ApiOperation(value = "根据id查询大事件信息", response = Article.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "大事件信息id",required = true,  dataTypeClass = Long.class)})
    @GetMapping(value = "/query")
    public Article queryMemorabiliaById(Long id) {
        return iArticleService.getById(id);
    }



    @ApiOperation(value = "根据id删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "大事件信息id", required = true, dataTypeClass = Long.class)})
    @PostMapping("/delete/{id}")
    public Boolean logicalDelete(@PathVariable(value="id") Long id){
        return iArticleService.logicalDeleteById(id);
    }
}
