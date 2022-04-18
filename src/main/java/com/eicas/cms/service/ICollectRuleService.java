package com.eicas.cms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eicas.cms.pojo.entity.CollectRule;
import com.eicas.cms.pojo.vo.CollectRuleVO;

import java.util.Map;


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

    /**
     * 查询所有自动采集的数据
     *
     * @param
     * @return
     */
    void selectCollectRuleInit();

    boolean updateByCollectId(Map flag);

    boolean updateCollectStatusById(Long id, Long status);

    boolean crawler(Long id);

}