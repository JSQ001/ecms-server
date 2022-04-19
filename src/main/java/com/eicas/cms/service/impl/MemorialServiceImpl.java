package com.eicas.cms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eicas.cms.mapper.MemorialMapper;
import com.eicas.cms.pojo.entity.MemorialEntity;
import com.eicas.cms.pojo.param.MemorialQueryParam;
import com.eicas.cms.service.IMemorialService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 大事记服务实现
 *
 * @author osnudt
 * @since 2022-04-18
 */
@Service
public class MemorialServiceImpl extends ServiceImpl<MemorialMapper, MemorialEntity> implements IMemorialService {

    @Resource
    MemorialMapper memorialMapper;

    @Override
    public Page<MemorialEntity> listMemorial(MemorialQueryParam param, Integer current, Integer size) {
        return memorialMapper.selectPage(Page.of(current, size),
                Wrappers.<MemorialEntity>lambdaQuery()
                        .between(MemorialEntity::getEventTime, param.getBeginEventTime(), param.getEndEventTime())
                        .between(MemorialEntity::getPublishTime, param.getBeginPublishTime(), param.getEndPublishTime())
                        .like(MemorialEntity::getTitle,param.getTitle())
                        .eq(MemorialEntity::getStatus, param.getStatus())
                        .orderByDesc(MemorialEntity::getEventTime));
    }
}
