package com.eicas.cms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.entity.ArticleEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eicas.cms.entity.AttachmentEntity;
import com.eicas.cms.pojo.param.*;
import com.eicas.cms.pojo.vo.ArticleStatisticCompileVO;
import com.eicas.cms.pojo.vo.ArticleStatisticVisitVO;
import com.eicas.common.ResultData;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * 文章信息表 服务类
 *
 * @author osnudt
 * @since 2022-04-19
 */
public interface IArticleService extends IService<ArticleEntity> {
    /**
     * 保存文章对象，校验并更新文章所属栏目信息
     *
     * @param entity 文章对象
     * @return 文章对象保存结果
     */
    ResultData<ArticleEntity> saveArticle(ArticleEntity entity);

    /**
     * 更新文章对象，校验并更新文章所属栏目信息
     *
     * @param entity 文章对象
     * @return 文章对象保存结果
     */
    ResultData<ArticleEntity> updateArticle(ArticleEntity entity);

    /**
     *
     * @param param
     * @param current
     * @param size
     * @return
     */
    Page<ArticleEntity> listArticle(ArticleQueryParam param, Integer current, Integer size);

    /**
     *
     * @param catalogId
     * @param current
     * @param size
     * @return
     */
    Page<ArticleEntity> listArticleByCatalogId(Long catalogId, Integer current, Integer size);

    /**
     *
     * @param param
     * @param current
     * @param size
     * @return
     */
    Page<ArticleEntity> listArticleByCatalogCode(ArticleQueryParam param, Integer current, Integer size);

    /**
     *
     * @param startTime
     * @param endTime
     * @return
     */
    ArticleStaticsResult statisticArticleVisit(LocalDateTime startTime, LocalDateTime endTime);

    /**statisticArticleVisit
     *
     * @param param
     * @return
     */
    ArticleStatisticVisitVO statisticArticleCompile(ArticleQueryParam param);

    /**
     *
     * @param id
     * @param catalogId
     * @return
     */
    Boolean moveArticle(Long id, Long catalogId);

    /**
     * 查询是否存在原文网址为originUrl的文章信息
     * @param originUrl 文章原文网址
     * @return 有返回真，无则返回假
     */
    Boolean hasRepetition(String originUrl);

    /**
     * 审核文章信息
     * @param param 审核参数
     * @return 有返回真，无则返回假
     */
    Boolean auditArticle(ArticleAuditParam param);


    /**
     * 统计指定栏目子栏目时间段内文章信息
     * @param code 父栏目code
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 新栏目对象
     */
    List<CatalogArticleStaticsResult> staticsCatalogArticle(String code, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 修改文章置顶、焦点、推荐、是否显示属性
     *
     * @param param 文章信息对象
     * @return 更新是否成功
     */
    ResultData updateArticleProperties(ModifyArticleParam param);

    /**
     * 上传附件，返回附件对象
     * @param file 文件对象
     * @return 附件对象
     */
    AttachmentEntity upload(MultipartFile file);
}
