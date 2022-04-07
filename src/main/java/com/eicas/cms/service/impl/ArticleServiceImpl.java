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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;

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
      /*  List<Long> ids = null;
        if(articleQueryVo.getColumnId() != null){
            ids = columnService.listIdsByParentId(articleQueryVo.getColumnId());
        }
        return articleMapper.listArticles(ids,articleQueryVo, articleQueryVo.pageFactory());  */


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
    public ArticleStatisticalResults getStatistics(LocalDateTime startTime, LocalDateTime endTime) {
        return articleMapper.statistics(startTime, endTime);
    }

    @Override
    public List<ArticleStatisticalResults> getStatisticByColumn(String code, LocalDateTime startTime, LocalDateTime endTime) {
        return articleMapper.statisticByColumn(code, startTime, endTime);
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




}
