package com.eicas.cms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.pojo.entity.Article;
import com.eicas.cms.pojo.vo.ArticleAuditVO;
import com.eicas.cms.pojo.vo.ArticleStatisticalResults;
import com.eicas.cms.pojo.vo.ArticleVO;
import com.eicas.cms.pojo.vo.StatisticalResults;
import com.eicas.cms.service.IArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* 文章信息表 前端控制器
* @author jsq
* @since 2022-03-05
*/
@Api(tags = "文章信息表")
@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Value("${filePath.loadPath}")
    private String loadPath;


    @Value("${filePath.image}")
    private  String imagePath;


    @Resource
    private IArticleService iArticleService;

    @ApiOperation(value = "文章信息表分页列表", response = Article.class)
    @GetMapping(value = "/list")
    public Page<Article> listArticles( ArticleVO articleQueryVo) {
        articleQueryVo.setIsMajor(null);
        articleQueryVo.setIsNotice(null);
        return iArticleService.listArticles(articleQueryVo);
    }

    @ApiOperation(value = "根据栏目columnecode查询文章信息表分页列表", response = Article.class)
    @GetMapping(value = "/listinfor")
    public  Page<Article> listArticlesInfor(ArticleVO articleVO){

        return iArticleService.listArticlesInfor(articleVO);

    }

    @ApiOperation(value = "根据id查询文章信息", response = ArticleStatisticalResults.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "文章id",required = true,  dataTypeClass = Long.class)})
    @GetMapping(value = "/query")
    public Article queryArticleById(Long id) {
        iArticleService.articlePoint(id);
        return iArticleService.getById(id);
    }

    //统计文章各状态总数
    @ApiOperation(value = "统计全部文章信息", response = Article.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间", dataTypeClass = LocalDateTime.class),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataTypeClass = LocalDateTime.class)
    })
    @GetMapping(value = "/statistics")
    public Map getStatistics(LocalDateTime startTime, LocalDateTime endTime) {
        String month="";
        return iArticleService.getStatistics(month,startTime, endTime);
    }



    //根据栏目，时间查询发布信息
    @ApiOperation(value = "统计指定栏目的子栏目文章信息", response = ArticleStatisticalResults.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "columnId", value = "栏目columnId", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "startTime", value = "开始时间", dataTypeClass = LocalDateTime.class),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataTypeClass = LocalDateTime.class)
    })
    @GetMapping(value = "/getStatisticsByColumn")
    public List<StatisticalResults> getStatisticByColumn(String columnId,LocalDateTime startTime, LocalDateTime endTime) {
        return iArticleService.getStatisticByColumn(columnId,startTime, endTime);
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
    public Boolean auditArticle(@Valid @RequestBody ArticleAuditVO articleAuditVO){
        return iArticleService.auditArticle(articleAuditVO);
    }


    @ApiOperation(value = "设置(取消设置)焦点新闻")
    @PostMapping("/modify/focus")
    public Boolean modifyFocus(Long id, Boolean isFocus){
        return iArticleService.modifyFocus(id, isFocus);
    }


    @ApiOperation(value = "文件上传")
    @PostMapping("/fileLoad")
    public String upload(HttpServletRequest request, @RequestParam("file")MultipartFile file) throws IOException{
        String realPath=request.getSession().getServletContext().getRealPath("/uploadFile");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String mk=sdf.format(new Date());

        String path= imagePath+mk+"/";//'ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/"+mk+"/";
          System.out.println("path="+path);
        File targetFile=new File(path);
       if (!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream bos=null;
        try {

            String str=targetFile+"\\"+file.getOriginalFilename();
             bos=new FileOutputStream(str);
             bos.write(file.getBytes());

            return loadPath+mk+"/"+file.getOriginalFilename();
        }catch (FileNotFoundException e){
            e.printStackTrace();
            return "";
        }finally {
            bos.flush();
            bos.close();
        }
    }

    @ApiOperation(value = "文章点击")
    @PostMapping("/point")
    public int articlePoint(@RequestParam("id") long id) {
           return iArticleService.articlePoint(id);
    }


    /**
     * 用户发表文章统计
     */
    @ApiOperation(value = "用户发表文章统计", response = ArticleStatisticalResults.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "createdBy", value = "用户id", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "startTime", value = "开始时间", dataTypeClass = LocalDateTime.class),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataTypeClass = LocalDateTime.class)
    })
    @GetMapping(value = "/statisticsByUser")
    public Map getStatisticByUser(String id,LocalDateTime startTime, LocalDateTime endTime) {
        return iArticleService.statisticsByUser(null);
    }

    /**
     * 统计自动采集节点发布信息
     * */
    @ApiOperation(value = "用户发表文章统计", response = ArticleStatisticalResults.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pointName", value = "节点名称", dataTypeClass = String.class),
            @ApiImplicitParam(name = "startTime", value = "开始时间", dataTypeClass = LocalDateTime.class),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataTypeClass = LocalDateTime.class)
    })
    @GetMapping(value = "/getStatisticsByPointName")
    public List<StatisticalResults> getStatisticsByPointName(String pointName,LocalDateTime startTime, LocalDateTime endTime) {
        Map<String,Object> userMap= new HashMap<>();
        userMap.put("pointName",pointName);
        userMap.put("startTime",startTime);
        userMap.put("endTime",endTime);
        return iArticleService.statisticsByPointName(userMap);
    }



    /**
     * 根据栏目，状态查询发布信息
     * */
    @ApiOperation(value = "根据栏目，状态查询发布信息", response = Article.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "state", value = "发布状态", dataTypeClass = String.class),
            @ApiImplicitParam(name = "columnId", value = "栏目if", dataTypeClass = String.class),
            @ApiImplicitParam(name = "startTime", value = "开始时间", dataTypeClass = LocalDateTime.class),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataTypeClass = LocalDateTime.class)
    })
    @GetMapping(value = "/getStatisticByColumnArticle")
    List<Article> statisticByColumnArticle(@Param("state") String state, @Param("columnId")String columnId, @Param("startTime")LocalDateTime startTime, @Param("endTime") LocalDateTime endTime) {
        return  iArticleService.statisticByColumnArticle(state,columnId,startTime,endTime);
    }




    @ApiOperation(value = "栏目浏览总量", response = Article.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "columnId", value = "栏目if", dataTypeClass = String.class),
            @ApiImplicitParam(name = "startTime", value = "开始时间", dataTypeClass = LocalDateTime.class),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataTypeClass = LocalDateTime.class)
    })
    @GetMapping(value = "/getStatisticsByHitNumsCount")
    Map statisticsByHitNumsCount( @Param("columnId")String columnId, @Param("startTime")LocalDateTime startTime, @Param("endTime") LocalDateTime endTime) {
        Map<String,Object> HitNumsMap= new HashMap<>();
        HitNumsMap.put("columnId",columnId);
        HitNumsMap.put("startTime",startTime);
        HitNumsMap.put("endTime",endTime);

        return  iArticleService.statisticsByHitNumsCount(HitNumsMap);
    }


    //============================================

    @ApiOperation(value = "栏目浏览总量前8", response = Article.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "columnId", value = "栏目if", dataTypeClass = String.class),
            @ApiImplicitParam(name = "startTime", value = "开始时间", dataTypeClass = LocalDateTime.class),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataTypeClass = LocalDateTime.class)
    })
    @GetMapping(value = "/getstatisticsByHitNumsCountBefore")
    List<StatisticalResults> statisticsByHitNumsCountBefore( @Param("columnId")String columnId, @Param("startTime")LocalDateTime startTime, @Param("endTime") LocalDateTime endTime) {
        Map<String,Object> HitNumsMap= new HashMap<>();
        HitNumsMap.put("columnId",columnId);
        HitNumsMap.put("startTime",startTime);
        HitNumsMap.put("endTime",endTime);

        return  iArticleService.statisticsByHitNumsCountBefore(HitNumsMap);
    }





}
