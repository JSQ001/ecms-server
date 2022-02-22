package com.eicas.ecms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eicas.ecms.entity.CollectionRule;

import java.util.List;

/**
 * <p>
 * 采集规则表 服务类
 * </p>
 *
 * @author jsq
 * @since 2021-11-09
 */
public interface ICollectionRuleService extends IService<CollectionRule> {

    /**
     * 采集规则表分页列表
     * @param
     * @return
     */
     Page<CollectionRule> page(CollectionRule entity, Page<CollectionRule> page);

    /**
     * 采集规则表详情
     * @param id id
     * @return CollectionRule
     */
    CollectionRule info(Long id);

    /**
    * 采集规则表新增
    * @param param 根据需要进行传值
    * @return
    */
    void add(CollectionRule param);

    /**
    * 采集规则表修改
    * @param param 根据需要进行传值
    * @return
    */
    void modify(CollectionRule param);

    /**
    * 采集规则表删除(单个条目)
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

    CollectionRule transforRuleDto(CollectionRule c);

    Page<CollectionRule> transforCollectionBoPage(CollectionRule entity, Page<CollectionRule> page);
}
