package com.eicas.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.pojo.entity.Article;
import com.eicas.cms.pojo.vo.ArticleVO;
import com.eicas.cms.pojo.vo.MemorbiliaVo;
import com.eicas.cms.pojo.vo.NotifyVo;
import com.eicas.cms.service.IArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import  org.springframework.beans.factory.annotation.Value;
import javax.annotation.Resource;
import javax.validation.Valid;

@Api(tags = "通知公告信息")
@RestController
@RequestMapping("/api/notify")
public class NotifyController {

    @Resource
    private IArticleService iArticleService;

    @Value("${column.notyfyColumnId}")
    private  long columnId;



    @ApiOperation(value = "通知公告新增/修改")
    @PostMapping(value = "/createOrUpdate")
    public Boolean createOrUpdate(@Valid @RequestBody NotifyVo entity) {

        Article article=new  Article();
        if (entity.getId()!=null&&entity.getId()>0){
            article.setId(entity.getId());

        }
        article.setContent(entity.getContent());
        article.setTitle(entity.getTitle());
        article.setPublishTime(entity.getPublishTime());
        article.setAuthor(entity.getAuthor());

        article.setColumnId(this.columnId);
        article.setState(entity.getState());
        article.setIsNotice(true);

        return iArticleService.createOrUpdate(article);
    }


    @ApiOperation(value = "通知公告信息表分页列表", response = Article.class)
    @GetMapping(value = "/list")
    public Page<Article> listNotifys(NotifyVo entity) {

        ArticleVO articleVO=new ArticleVO();
        articleVO.setTitle(entity.getTitle());
        articleVO.setStartTime(entity.getStartTime());
        articleVO.setEndTime(entity.getEndTime());
        articleVO.setIsNotice(Boolean.TRUE);
        return iArticleService.listArticles(articleVO);
    }



    @ApiOperation(value = "根据id查询通知公告信息", response = Article.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "通知公告信息id",required = true,  dataTypeClass = Long.class)})
    @GetMapping(value = "/query")
    public Article queryNotifyById(Long id) {
        return iArticleService.getById(id);
    }



    @ApiOperation(value = "根据id删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "通知公告id", required = true, dataTypeClass = Long.class)})
    @PostMapping("/delete/{id}")
    public Boolean logicalDelete(@PathVariable(value="id") Long id){
        return iArticleService.logicalDeleteById(id);
    }






}
