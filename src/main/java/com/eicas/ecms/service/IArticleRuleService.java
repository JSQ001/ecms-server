package com.eicas.ecms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eicas.ecms.dto.NumDto;
import com.eicas.ecms.entity.Ecms;
import com.eicas.ecms.entity.ArticleRule;
import com.eicas.ecms.dto.ArticleRuleDto;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author jsq
 * @since 2021-10-28
 */
public interface IArticleRuleService extends IService<Ecms> {

    /**
     * 文章表详情
     * @param id
     * @return
     */
    Ecms info(Long id);

    /**
    * 文章表新增
    * @param param 根据需要进行传值
    * @return
    */
    void add(ArticleRule param);

    /**
    * 文章表修改
    * @param param 根据需要进行传值
    * @return
    */
    void modify(ArticleRule param);

    /**
    * 文章表删除(单个条目)
    * @param id
    * @return
    */
    void remove(Long id);

    /**
    * 删除(多个条目)
    * @param ids
    * @return
    */
    void removes(List<Long> ids);

    /**
     * 分页获取文章规则
     */
    PageInfo<ArticleRuleDto> page(ArticleRuleDto articleRuleDto);

    /**
     * 按照时间栏目获取采集数量，传空则为查所有
     * @param articleRuleDto
     * @return
     */
    NumDto selectNum(ArticleRuleDto articleRuleDto);


    Page<Ecms> getEntityMsg(ArticleRuleDto articleRuleDto,Page<Ecms> page);

    Page<Ecms> getEntityClsMsg(ArticleRuleDto articleRuleDto, Page<Ecms> page);
}
