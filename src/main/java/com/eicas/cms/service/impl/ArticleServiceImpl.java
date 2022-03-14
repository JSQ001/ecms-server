package com.eicas.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eicas.cms.component.MyWebsocketServer;
import com.eicas.cms.pojo.entity.Article;
import com.eicas.cms.pojo.vo.ArticleAuditVO;
import com.eicas.cms.pojo.vo.ArticleVO;
import com.eicas.cms.pojo.vo.ArticleStatisticalResults;
import com.eicas.cms.pojo.vo.WebSocketResponseToClient;
import com.eicas.cms.mapper.ArticleMapper;
import com.eicas.cms.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import java.util.List;


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

    @Override
    public Page<Article> listArticles(ArticleVO articleQueryVo) {
        return articleMapper.listArticles(articleQueryVo, articleQueryVo.pageFactory());
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
        QueryWrapper<Article> queryWrapper = new QueryWrapper<Article>()
                .select("id")
                .eq(entity.getContent() != null, "column_id",entity.getColumnId())
                .eq(entity.getSource() != null, "source",entity.getSource())
                .eq(entity.getTitle() != null, "title",entity.getTitle())
                .eq(entity.getAuthor() != null, "author",entity.getAuthor());

        List<Article> article = this.list(queryWrapper);
        if(article.size() > 0){
            log.info("文章已存在");
            if(StringUtils.hasText(sessionId)){
                MyWebsocketServer.sendMessage(sessionId, new WebSocketResponseToClient(201, "文章已存在"));
            }
            return false;
        }else {
            if(StringUtils.hasText(sessionId)){
                MyWebsocketServer.sendMessage(sessionId, new WebSocketResponseToClient(200, "爬取成功"));
            }
            return customSave(entity);
        }
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
    public boolean auditArticle(List<ArticleAuditVO> list) {
        return articleMapper.auditArticle(list);
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


}
