package com.eicas.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eicas.cms.entity.ArticleEntity;
import com.eicas.cms.entity.AttachmentEntity;
import com.eicas.cms.entity.AttachmentRefEntity;
import com.eicas.cms.entity.CatalogEntity;
import com.eicas.cms.mapper.ArticleMapper;
import com.eicas.cms.pojo.param.*;
import com.eicas.cms.pojo.vo.ArticleStatisticVisitVO;
import com.eicas.cms.service.*;
import com.eicas.common.ResultData;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 文章信息服务实现类
 *
 * @author osnudt
 * @since 2022-04-19
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, ArticleEntity> implements IArticleService {
    @Resource
    ArticleMapper articleMapper;

    @Resource
    ICatalogService catalogService;

    @Resource
    IAttachmentService attachmentService;

    @Resource
    IAttachmentRefService attachmentRefService;

    @Override
    public ResultData<ArticleEntity> saveArticle(ArticleEntity entity) {
        Long catalogId = entity.getCatalogId();

        CatalogEntity catalogEntity = catalogService.getById(catalogId);
        if (catalogEntity == null) {
            return ResultData.failed("指定文章所属栏目不存在！");
        }

        if (!catalogService.isLeafCatalog(catalogId)) {
            return ResultData.failed("指定文章所属栏目不存在不是叶子节点！");
        }

        entity.setCatalogName(catalogEntity.getCatalogName());
        entity.setCatalogEncode(catalogEntity.getCatalogName());
        this.save(entity);

        return ResultData.success(entity, "保存文章信息成功！");
    }

    @Override
    public ResultData<ArticleEntity> updateArticle(ArticleEntity entity) {
        this.updateById(entity);
        return ResultData.success(entity);
    }

    @Override
    public Page<ArticleEntity> listArticle(ArticleQueryParam param, Integer current, Integer size) {
        return articleMapper.listArticle(param, Page.of(current,size));
    }

    @Override
    public Page<ArticleEntity> listArticleByCatalogId(Long catalogId, Integer current, Integer size) {
        return articleMapper.selectPage(Page.of(current, size),
                Wrappers.<ArticleEntity>lambdaQuery()
                        .eq(ArticleEntity::getCatalogId, catalogId));
    }

    @Override
    public Page<ArticleEntity> listArticleByCatalogCode(ArticleQueryParam param, Integer current, Integer size) {
        return articleMapper.listArticleByCatalogCode(param, Page.of(current,size));
    }

    @Override
    public ArticleStatisticVisitVO statisticArticleCompile(ArticleQueryParam param) {
        return null;
    }

    @Override
    public ArticleStaticsResult statisticArticleVisit(LocalDateTime startTime, LocalDateTime endTime) {
        return articleMapper.statisticArticleInfoAndVisit(startTime, endTime);
    }

    @Override
    public Boolean moveArticle(Long id, Long catalogId) {
        CatalogEntity catalogEntity = catalogService.getById(catalogId);
        String catalogName = catalogEntity.getCatalogName();
        String treeRel = catalogEntity.getTreeRel();

        LambdaUpdateWrapper<ArticleEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(ArticleEntity::getCatalogId, catalogId)
                .set(ArticleEntity::getCatalogName, catalogName)
                .set(ArticleEntity::getCatalogEncode, treeRel);
        return update(wrapper);
    }

    @Override
    public Boolean hasRepetition(String originUrl) {
        return !articleMapper.selectList(
                Wrappers.<ArticleEntity>lambdaQuery()
                        .eq(ArticleEntity::getOriginUrl, originUrl)).isEmpty();
    }

    @Override
    public Boolean auditArticle(ArticleAuditParam param) {
        if(param.getAuditTime() == null){
            param.setAuditTime(LocalDateTime.now());
        }
        UpdateWrapper<ArticleEntity> wrapper = new UpdateWrapper<>();
        wrapper.lambda()
                .set(ArticleEntity::getState, param.getState())
                //.set(ArticleEntity::getAuditUserId, param.getAuditUserId())
               // .set(StringUtils.hasText(param.getAuditComments()), ArticleEntity::getAuditComments, param.getAuditComments())
                .in(ArticleEntity::getId,param.getArticleIds());
        return update(wrapper);
    }

    @Override
    public List<CatalogArticleStaticsResult> staticsCatalogArticle(String code, LocalDateTime startTime, LocalDateTime endTime) {
        return articleMapper.staticsCatalogArticle(code,startTime,endTime);
    }

    @Override
    public ResultData updateArticleProperties(ModifyArticleParam param) {
        if(param.getFocus() != null){
            if(!StringUtils.hasText(getById(param.getId()).getCoverImgUrl())){
                return ResultData.failed("该文章无标题图片，不能被设置为焦点新闻");
            }
        }
        ArticleEntity article = new ArticleEntity()
                .setId(param.getId())
                .setTop(param.getTop())
                .setFocus(param.getFocus())
                .setRecommended(param.getRecommended())
                .setVisible(param.getVisible());
        if(!updateById(article)){
            return ResultData.failed("设置失败！");
        }
        return ResultData.success("设置成功！");
    }

    @Override
    public AttachmentEntity upload(MultipartFile file) {
        AttachmentEntity attachment = attachmentService.upload(file);
        new AttachmentRefEntity()
                .setAttachmentId(attachment.getId())
                .setKeyId(1l)
                .setSymbol("article");
        //attachmentRefService.save()
        return attachment;
    }
}
