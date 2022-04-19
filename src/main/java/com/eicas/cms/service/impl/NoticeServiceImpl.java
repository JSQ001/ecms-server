package com.eicas.cms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eicas.cms.mapper.NoticeMapper;
import com.eicas.cms.pojo.entity.NoticeEntity;
import com.eicas.cms.pojo.param.NoticeQueryParam;
import com.eicas.cms.service.INoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 通知公告表 服务实现类
 * </p>
 *
 * @author osnudt
 * @since 2022-04-18
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, NoticeEntity> implements INoticeService {

    @Resource
    NoticeMapper noticeMapper;

    @Override
    public Page<NoticeEntity> listNotice(NoticeQueryParam param, Integer current, Integer size) {
        return noticeMapper.selectPage(Page.of(current, size),
                Wrappers.<NoticeEntity>lambdaQuery()
                        .between(NoticeEntity::getPublishTime, param.getBeginPublishTime(), param.getEndPublishTime())
                        .eq(NoticeEntity::getPublisher, param.getPublisher())
                        .like(NoticeEntity::getTitle, param.getTitle())
                        .eq(NoticeEntity::getPublishUnit, param.getPublishUnit())
                        .eq(NoticeEntity::getStatus, param.getStatus())
                        .orderByDesc(NoticeEntity::getPublishTime));
    }
}
