package com.eicas.cms.service.impl;

import com.eicas.cms.component.ScheduleTask;
import com.eicas.cms.pojo.entity.CollectRule;
import com.eicas.cms.mapper.CollectRuleMapper;
import com.eicas.cms.pojo.vo.CollectRuleVO;
import com.eicas.cms.service.ICollectRuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;


/**
 * <p>
 * 采集规则表 服务实现类
 * </p>
 *
 * @author jsq
 * @since 2022-03-06
*/
@Service
@Transactional
public class CollectRuleServiceImpl extends ServiceImpl<CollectRuleMapper, CollectRule> implements ICollectRuleService {

    @Resource
    private CollectRuleMapper collectRuleMapper;

    @Resource
    private ScheduleTask scheduleTask;

    @Override
    public Page<CollectRule> listCollectRules(CollectRuleVO collectRuleVO) {
        return collectRuleMapper.listArticles(collectRuleVO, collectRuleVO.pageFactory());
    }

    @Override
    public boolean createOrUpdate(CollectRule entity){
        boolean flag = saveOrUpdate(entity);
        scheduleTask.update(entity);
        return flag;
    }

    @Override
    public boolean logicalDeleteById(Long id){
        return removeById(id);
    }

}
