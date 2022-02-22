package com.eicas.ecms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eicas.ecms.entity.CollectionRule;
import com.eicas.ecms.entity.Column;
import com.eicas.ecms.entity.CronCrawl;
import com.eicas.ecms.mapper.CollectionRuleMapper;
import com.eicas.ecms.mapper.ColumnMapper;
import com.eicas.ecms.mapper.CronCrawlMapper;
import com.eicas.ecms.service.ICollectionRuleService;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 采集规则表 服务实现类
 * </p>
 *
 * @author jsq
 * @since 2021-11-09
*/
@Service
public class CollectionRuleServiceImpl extends ServiceImpl<CollectionRuleMapper, CollectionRule> implements ICollectionRuleService {


    @Resource
    private CollectionRuleMapper collectionRuleMapper;
    @Resource
    private CronCrawlMapper cronCrawlMapper;
    @Resource
    private ColumnMapper columnMapper;
    /**
     * 采集规则表分页列表
     * @param param 根据需要进行传值
     * @return page
     */
    @Override
    public Page<CollectionRule> page(CollectionRule param, Page<CollectionRule> page) {

        return collectionRuleMapper.page(param, page);
    }


    @Override
    public CollectionRule info(Long id) {
    return null;
    }

    @Override
    public void add(CollectionRule param) {

    }

    @Override
    public void modify(CollectionRule param) {

    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void removes(List<Long> ids) {

    }

    @Override
    public CollectionRule transforRuleDto(CollectionRule entity) {
        CronCrawl cronCrawl = cronCrawlMapper.selectOne(new QueryWrapper<CronCrawl>().eq("rule_id", entity.getId()));

        if (cronCrawl != null) {
            String cron = cronCrawl.getCron();
            if (cronCrawl.getIsDeleted() == 0) {
                CronExpression expression = CronExpression.parse(cron);
                entity.setLocalDateTime(expression.next(LocalDateTime.now()));
                entity.setAutoCollect(true);
            } else {
                entity.setLocalDateTime(null);
                entity.setAutoCollect(false);
            }
        }

        return entity;
    }

    @Override
    public Page<CollectionRule> transforCollectionBoPage(CollectionRule entity, Page<CollectionRule> page) {
        Page<CollectionRule> collectionRulePage = collectionRuleMapper.selectPage(page, new QueryWrapper<>(entity).eq("is_deleted", 0));
        for (CollectionRule collectionRule:collectionRulePage.getRecords()) {
            Column column = columnMapper.selectOne(new QueryWrapper<Column>().eq("id", collectionRule.getColumnId()));
            collectionRule.setColumnName(column.getColumnName());
            CronCrawl cronCrawl = cronCrawlMapper.selectOne(new QueryWrapper<CronCrawl>().eq("rule_id", collectionRule.getId()).eq("is_deleted", 0));
            if (cronCrawl != null) {
                String cron = cronCrawl.getCron();
                String second = cron.split(" ")[0];
                String minute = cron.split(" ")[1];
                String hour   = cron.split(" ")[2];
                String cronTime = hour + ":" + minute + ":" + second;
                collectionRule.setCron(cronTime);

                CronExpression cronExpression = CronExpression.parse(cron);
                if (cronCrawl.getIsDeleted() == 0) {
                    collectionRule.setLocalDateTime(cronExpression.next(LocalDateTime.now()));
                    collectionRule.setAutoCollect(true);
                } else {
                    collectionRule.setAutoCollect(false);
                }

            } else {
                collectionRule.setCron(null);
                collectionRule.setAutoCollect(false);
            }
        }
        return collectionRulePage;
    }
}
