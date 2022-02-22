package com.eicas.ecms.service.impl;

import com.eicas.ecms.entity.CronCrawl;
import com.eicas.ecms.mapper.CronCrawlMapper;
import com.eicas.ecms.service.ICronCrawlService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zyt
 * @since 2022-01-11
*/
@Service
public class CronCrawlServiceImpl extends ServiceImpl<CronCrawlMapper, CronCrawl> implements ICronCrawlService {

    @Autowired
    private CronCrawlMapper cronCrawlMapper;
    /**
     * 分页列表
     * @param param 根据需要进行传值
     * @return
     */
    @Override
    public Page<CronCrawl> page(CronCrawl param, Page<CronCrawl> page) {

        QueryWrapper<CronCrawl> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            // 主键
                    .eq(StringUtils.hasText(param.getId()), CronCrawl::getId, param.getId())
                    // 采集规则id
                    .eq(StringUtils.hasText(param.getRuleId()), CronCrawl::getRuleId, param.getRuleId())
                    // 任务名称
                    .eq(StringUtils.hasText(param.getJName()), CronCrawl::getJName, param.getJName())
                    // 任务组
                    .eq(StringUtils.hasText(param.getJGroup()), CronCrawl::getJGroup, param.getJGroup())
                    // 触发器名称
                    .eq(StringUtils.hasText(param.getTName()), CronCrawl::getTName, param.getTName())
                    // 触发器组
                    .eq(StringUtils.hasText(param.getTGroup()), CronCrawl::getTGroup, param.getTGroup())
                    // cron表达式，定时采集
                    .eq(StringUtils.hasText(param.getCron()), CronCrawl::getCron, param.getCron())
                    // 
                    .eq(param.getCreatedTime() != null, CronCrawl::getCreatedTime, param.getCreatedTime())
                    // 
                    .eq(StringUtils.hasText(param.getCreatedBy()), CronCrawl::getCreatedBy, param.getCreatedBy())
                    // 
                    .eq(param.getUpdatedTime() != null, CronCrawl::getUpdatedTime, param.getUpdatedTime())
                    // 
                    .eq(StringUtils.hasText(param.getUpdatedBy()), CronCrawl::getUpdatedBy, param.getUpdatedBy())
                    // 逻辑删除 0:未删除 1:删除
                    .eq(param.getIsDeleted() != null, CronCrawl::getIsDeleted, param.getIsDeleted())
        ;

        return page(page, queryWrapper);
    }

    @Override
    public CronCrawl info(Long id) {
    return null;
    }

    @Override
    public void add(CronCrawl param) {

    }

    @Override
    public void modify(CronCrawl param) {

    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void removes(List<Long> ids) {

    }

    @Override
    public Boolean delete() {

        return false;
    }
}
