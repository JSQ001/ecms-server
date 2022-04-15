package com.eicas.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
//import com.eicas.cms.component.MyWebsocketServer;
import com.eicas.cms.exception.BusinessException;
import com.eicas.cms.pojo.entity.Article;
import com.eicas.cms.pojo.enumeration.ResultCode;
import com.eicas.cms.pojo.vo.*;
import com.eicas.cms.mapper.ArticleMapper;
import com.eicas.cms.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.service.ICollectRuleService;
import com.eicas.cms.service.IColumnService;
import com.eicas.cms.utils.MinusOneDayUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 文章信息表 服务实现类
 * </p>
 *
 * @author jsq
 * @since 2022-03-05
*/
@Service
@Transactional
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private IColumnService columnService;

    @Resource
    private ICollectRuleService iCollectRuleService;


    @Override
    public Page<Article> listArticles(ArticleVO articleQueryVo) {

        return articleMapper.listArticlesA(articleQueryVo, articleQueryVo.pageFactory());



    }



    /**
    * 保存或更新
    * @param entity
    * @return
    */
    @Override
    public boolean createOrUpdate(Article entity){
        return entity.getId() != null ? updateById(entity) : customSave(entity);
    }

    /**
     * 保存爬取的文章
     */
    @Override
    public boolean saveCrawl(Article entity, String sessionId){
        //去重入库
       /* QueryWrapper<Article> queryWrapper = new QueryWrapper<Article>()
                .select("id")
                .eq(entity.getColumnId() != null, "column_id",entity.getColumnId())
                .eq( StringUtils.hasText(entity.getContent()), "content",entity.getContent())
                .eq( StringUtils.hasText(entity.getSource()), "source",entity.getSource())
                .eq( StringUtils.hasText(entity.getTitle()), "title",entity.getTitle())
                .eq( StringUtils.hasText(entity.getAuthor()), "author",entity.getAuthor());


        List<Article> article = this.list(queryWrapper);

        */ //columnId  content  source  title  author

        if  (entity.getContent()==null||entity.getContent().equals("")){
            return false;
        }

        if  (entity.getTitle()==null||entity.getTitle().equals("")){
            return false;
        }

        Map<String,Object> duplicateMap= new HashMap<>();
        duplicateMap.put("columnId",entity.getColumnId());
        duplicateMap.put("title",entity.getTitle());
        duplicateMap.put("author",entity.getAuthor());
        duplicateMap.put("ontent",entity.getContent());

        boolean flag = false;


        if(articleMapper.listCount(duplicateMap)> 0){
            log.info("文章已存在");
        }else {


            if (entity.getEssential()==null||entity.getEssential().equals("")){
                entity.setEssential(entity.getContent().substring(0,100));
            }
            flag =  customSave(entity);

        }
       // int count = MyWebsocketServer.pageQueue.get(sessionId);
        //MyWebsocketServer.pageQueue.put(sessionId, count -1 );

        //if(StringUtils.hasText(sessionId) && MyWebsocketServer.pageQueue.get(sessionId) == 0){
          //  MyWebsocketServer.sendMessage(sessionId, new WebSocketResponseToClient(200, "爬取成功！"));
       // }
        return flag;
    }

    @Override
    public Map getStatistics(String monthTime,LocalDateTime startTime, LocalDateTime endTime) {
        Map<String,Object> resultMap= new HashMap<>();
        LocalDate  localDate=LocalDate.now();
        DateTimeFormatter sdf=DateTimeFormatter.ofPattern("MM");
        String month=sdf.format(localDate);
        System.out.println("month="+month);

        resultMap.put("alldate",articleMapper.statistics(monthTime,startTime, endTime));
        resultMap.put("monthTime",articleMapper.statistics(month,startTime, endTime));

        Map<String,Object> hitNumsMap= new HashMap<>();
        hitNumsMap.put("columnId",null);
        hitNumsMap.put("startTime",null);
        hitNumsMap.put("endTime",null);
        hitNumsMap.put("monthTime",null);

        if (articleMapper.statisticsByHitNumsCount(hitNumsMap).size()>0)
          resultMap.put("hitnums", articleMapper.statisticsByHitNumsCount(hitNumsMap).get(0).getAllData());
        else resultMap.put("hitnums",'0');


        hitNumsMap.put("monthTime",month);
        if (articleMapper.statisticsByHitNumsCount(hitNumsMap).size()>0)
             resultMap.put("hitnumsmonth",articleMapper.statisticsByHitNumsCount(hitNumsMap));
        else  resultMap.put("hitnumsmonth",'0');


        return resultMap;
    }

    @Override
    public List<StatisticalResults> getStatisticByColumn(String columnId,LocalDateTime startTime, LocalDateTime endTime) {
        return articleMapper.statisticByColumn(columnId,startTime, endTime);
    }

    @Override
    public boolean auditArticle(ArticleAuditVO articleAuditVO) {
        if(articleAuditVO.getAuditTime() == null){
            articleAuditVO.setAuditTime(LocalDateTime.now());
        }
        UpdateWrapper<Article> wrapper = new UpdateWrapper<>();
        wrapper.lambda()
                .set(Article::getState, articleAuditVO.getState())
                .set(Article::getAuditUserId, articleAuditVO.getAuditUserId())
                .set(StringUtils.hasText(articleAuditVO.getAuditComments()), Article::getAuditComments, articleAuditVO.getAuditComments())
                .in(Article::getId,articleAuditVO.getArticleIds());
        return update(wrapper);
    }

    @Override
    public boolean modifyFocus(Long id, boolean isFocus) {
        Article article = getById(id);
        if(article == null){
            return false;
        }
        if(!StringUtils.hasText(article.getCoverImgUrl())){
            throw new BusinessException(ResultCode.ARTICLE_NO_FOCUS_IMG);
        }
        article.setIsFocus(isFocus);

        return updateById(article);
    }

    /**
     * 新建文章，设置状态为编辑中
     * */
    public boolean customSave(Article entity) {
        entity.setState(1);
        return save(entity);
    }

    /**
    * 根据id逻辑删除
    * @param id
    * @return
    */
    @Override
    public boolean logicalDeleteById(Long id){
        return removeById(id);
    }

    @Override
    public boolean batchDel(List<Long> ids) {
        return removeBatchByIds(ids);
    }

    /**
     *自动采集重复查询
     * */
    @Override
    public  int listCount(Map paramMap){
        return  articleMapper.listCount(paramMap);
    }



    /**文章点击次数*/
    @Override
    public int  articlePoint(long id){
        try {
            articleMapper.articlePoint(id);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }

    }


    /**
     * 用户发表文章统计
     */

    public  Map statisticsByUser(Map parmaMap){
         try {
             Map<String,Object> resultMap= new HashMap<>();

             //栏目发布前8
             if (articleMapper.statisticByColumn(null,null, null).size()>0){
                 resultMap.put("columnstatisbefore8",articleMapper.statisticByColumn(null,null, null));
             }else resultMap.put("columnstatisbefore8",'0');
             //用户发布前8
             if (articleMapper.statisticsByUser(null).size()>0){
                 resultMap.put("userstatisbefore8",articleMapper.statisticsByUser(null));
             }
             else resultMap.put("userstatisbefore8",'0');

             //自动采集节点发布前8
             if (articleMapper.statisticsByPointName(null).size()>0){
                resultMap.put("statisticsPointbefore8",articleMapper.statisticsByPointName(null));
             }else resultMap.put("statisticsPointbefore8",0);

             //====================================================

            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }



    /**
     * 统计自动采集节点发布信息
     * */
    @Override
    public List<StatisticalResults> statisticsByPointName(Map parmaMap){
          return articleMapper.statisticsByPointName(parmaMap);

    }


    @Override
    public  List<Article> statisticByColumnArticle(@Param("state") String state, @Param("columnId")String columnId, @Param("startTime")LocalDateTime startTime, @Param("endTime") LocalDateTime endTime){
           return  articleMapper.statisticByColumnArticle(state,columnId,startTime,endTime);
    }

    //栏目浏览量排行  网站总流量1-10

    @Override
    public  Map statisticsByHitNumsCount(Map parmaMap){

        Map<String,Object> resultMap= new HashMap<>();

        Map<String,Object> tempMap= new HashMap<>();

        //栏目标题浏览总量前8

        if (articleMapper.statisticsByHitNumsCountBefore(null).size()>0){
            resultMap.put("hitnumscountbefore8",articleMapper.statisticsByHitNumsCountBefore(null));
        }
        else resultMap.put("hitnumscountbefore8",0);

        //网站总浏览量每天1-10
        //StatisticalResults statisticsByHitNumsCountEveryDay(String dateNum);
        String dateTemp="";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");


        int itemp=0;
        if (articleMapper.statisticsByHitNumsCountEveryDay("1")!=null)
        {
            tempMap.put(sdf.format(new Date()),Integer.decode(articleMapper.statisticsByHitNumsCountEveryDay("1").getAllData()));
            itemp=Integer.decode(articleMapper.statisticsByHitNumsCountEveryDay("1").getAllData());

        }
        else tempMap.put(sdf.format(new Date()),0);

        dateTemp= MinusOneDayUtil.minusOneDay(sdf.format(new Date()));

        if (articleMapper.statisticsByHitNumsCountEveryDay("2")!=null)
        {

            tempMap.put(dateTemp,Integer.decode(articleMapper.statisticsByHitNumsCountEveryDay("2").getAllData())- itemp);
            itemp=Integer.decode(articleMapper.statisticsByHitNumsCountEveryDay("2").getAllData());}
        else tempMap.put(dateTemp,0);

        dateTemp= MinusOneDayUtil.minusOneDay(dateTemp);

        if (articleMapper.statisticsByHitNumsCountEveryDay("3")!=null)
        {
            tempMap.put(dateTemp,Integer.decode(articleMapper.statisticsByHitNumsCountEveryDay("3").getAllData())- itemp);
            itemp=Integer.decode(articleMapper.statisticsByHitNumsCountEveryDay("3").getAllData());
        }
        else tempMap.put(dateTemp,0);

        dateTemp= MinusOneDayUtil.minusOneDay(dateTemp);

        if (articleMapper.statisticsByHitNumsCountEveryDay("4")!=null)
        {
            tempMap.put(dateTemp,Integer.decode(articleMapper.statisticsByHitNumsCountEveryDay("4").getAllData())- itemp);
            itemp=Integer.decode(articleMapper.statisticsByHitNumsCountEveryDay("4").getAllData());
        }
        else tempMap.put(dateTemp,0);


        dateTemp= MinusOneDayUtil.minusOneDay(dateTemp);

        if (articleMapper.statisticsByHitNumsCountEveryDay("5")!=null)
        {
            tempMap.put(dateTemp,Integer.decode(articleMapper.statisticsByHitNumsCountEveryDay("5").getAllData())- itemp);
            itemp=Integer.decode(articleMapper.statisticsByHitNumsCountEveryDay("5").getAllData());
        }
        else tempMap.put(dateTemp,0);


        dateTemp= MinusOneDayUtil.minusOneDay(dateTemp);

        if (articleMapper.statisticsByHitNumsCountEveryDay("6")!=null)
        {
            tempMap.put(dateTemp,Integer.decode(articleMapper.statisticsByHitNumsCountEveryDay("6").getAllData())- itemp);
            itemp=Integer.decode(articleMapper.statisticsByHitNumsCountEveryDay("6").getAllData());
        }
        else tempMap.put(dateTemp,0);


        dateTemp= MinusOneDayUtil.minusOneDay(dateTemp);

        if (articleMapper.statisticsByHitNumsCountEveryDay("7")!=null)
        {
            tempMap.put(dateTemp,Integer.decode(articleMapper.statisticsByHitNumsCountEveryDay("7").getAllData())- itemp);
            itemp=Integer.decode(articleMapper.statisticsByHitNumsCountEveryDay("7").getAllData());
        }
        else tempMap.put(dateTemp,0);


        dateTemp= MinusOneDayUtil.minusOneDay(dateTemp);

        if (articleMapper.statisticsByHitNumsCountEveryDay("8")!=null)
        {
            tempMap.put(dateTemp,Integer.decode(articleMapper.statisticsByHitNumsCountEveryDay("8").getAllData())- itemp);
            itemp=Integer.decode(articleMapper.statisticsByHitNumsCountEveryDay("8").getAllData());
        }
        else tempMap.put(dateTemp,0);

        dateTemp= MinusOneDayUtil.minusOneDay(dateTemp);

        if (articleMapper.statisticsByHitNumsCountEveryDay("9")!=null)
        {
            tempMap.put(dateTemp,Integer.decode(articleMapper.statisticsByHitNumsCountEveryDay("9").getAllData())- itemp);
            itemp=Integer.decode(articleMapper.statisticsByHitNumsCountEveryDay("9").getAllData());
        }
        else tempMap.put(dateTemp,0);


        dateTemp= MinusOneDayUtil.minusOneDay(dateTemp);

        if (articleMapper.statisticsByHitNumsCountEveryDay("10")!=null)
        {
            tempMap.put(dateTemp,Integer.decode(articleMapper.statisticsByHitNumsCountEveryDay("10").getAllData())- itemp);
            itemp=Integer.decode(articleMapper.statisticsByHitNumsCountEveryDay("10").getAllData());
        }
        else tempMap.put(dateTemp,0);

     resultMap.put("date",tempMap);






        return  resultMap;

    }




    /**
     * 某栏目总浏览量前8
     *
     * */
    @Override
    public  List<StatisticalResults> statisticsByHitNumsCountBefore(Map parmaMap){

        return  articleMapper.statisticsByHitNumsCountBefore(parmaMap);

    }


    @Override
    public  Page<Article> listArticlesInfor(ArticleVO articleVO){

        return   articleMapper.listArticlesInfor(articleVO, articleVO.pageFactory());
    }


}
