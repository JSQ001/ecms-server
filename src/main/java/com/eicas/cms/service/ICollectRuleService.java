package com.eicas.cms.service;

import com.eicas.cms.pojo.entity.CollectRule;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.pojo.vo.CollectRuleVO;


/**
 * <p>
 * 采集规则表 服务类
 * </p>
 *
 * @author jsq
 * @since 2022-03-06
 */
public interface ICollectRuleService extends IService<CollectRule> {

    /**
    * 根据传入entity属性值分页查询
    * @param collectRuleVO
    * @return Page<CollectRule>
    */
    Page<CollectRule> listCollectRules(CollectRuleVO collectRuleVO);

    /**
    * 保存或更新
    * @param entity
    * @return
    */
    boolean createOrUpdate(CollectRule entity);
    /**
    * 根据id逻辑删除
    * @param id
    * @return
    */
    boolean logicalDeleteById(Long id);

}