package com.eicas.ecms.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.eicas.ecms.bo.ListAuditBo;
import com.eicas.ecms.entity.CollectionRule;
import com.eicas.ecms.entity.Column;
import com.eicas.ecms.entity.Ecms;
import com.eicas.ecms.entity.ArticleRule;
import com.eicas.ecms.mapper.ColumnMapper;
import com.eicas.ecms.mapper.EcmsMapper;
import com.eicas.ecms.mapper.ArticleRuleMapper;
import com.eicas.ecms.mapper.CollectionRuleMapper;
import com.eicas.ecms.pipeline.ZZPipeline;
import com.eicas.ecms.processor.XpathPageProcessor;
import com.eicas.ecms.processor.ZZPageProcessor;
import com.eicas.ecms.service.IEcmsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.ecms.utils.download.WebDriverDownloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author jsq
 * @since 2021-10-28
 */
@Service
public class EcmsServiceImpl extends ServiceImpl<EcmsMapper, Ecms> implements IEcmsService {

    @Resource
    private EcmsMapper ecmsMapper;
    @Resource
    private ColumnMapper columnMapper;
    @Autowired
    final private CollectionRuleMapper collectionRuleMapper;
    @Autowired
    final private ArticleRuleMapper articleRuleMapper;

    final private ZZPipeline zzPipeline;

    final private ZZPageProcessor zzPageProcessor;

    final private XpathPageProcessor xpathPageProcessor;


    @Autowired
    public EcmsServiceImpl(CollectionRuleMapper collectionRuleMapper, ArticleRuleMapper articleRuleMapper, ZZPipeline zzPipeline, ZZPageProcessor zzPageProcessor, XpathPageProcessor xpathPageProcessor) {
        this.collectionRuleMapper = collectionRuleMapper;
        this.articleRuleMapper = articleRuleMapper;
        this.zzPipeline = zzPipeline;
        this.zzPageProcessor = zzPageProcessor;
        this.xpathPageProcessor = xpathPageProcessor;
    }

    /**
     * 文章表分页列表
     *
     * @param param 根据需要进行传值
     * @return page
     */
    @Override
    public Page<Ecms> page(Ecms param, Page<Ecms> page) {
        return ecmsMapper.page(param, page);
    }

    @Override
    public Ecms info(Long id) {
        return null;
    }

    @Override
    public void add(Ecms param) {
        param.setStatus(0);
        param.setCreatedTime(LocalDateTime.now());
        ecmsMapper.insert(param);
    }

    @Override
    public void modify(Ecms param) {
        ecmsMapper.updateById(param);
        ArticleRule articleRule = articleRuleMapper.selectByArticle(param.getId());
        articleRule.setStatus(param.getStatus());
        articleRuleMapper.updateById(articleRule);
    }

    @Override
    public void remove(Long id) {
        ecmsMapper.deleteById(id);
    }

    @Override
    public void removes(List<Long> ids) {

    }

    @Override
    public Page<Ecms> getPageByStatus(Integer status, Page<Ecms> page) {
        QueryWrapper<Ecms> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", status);
        return ecmsMapper.selectPage(page, queryWrapper);
    }

    @Override
    public String getQ(String id) {

        //根据id查找规则
        CollectionRule collectionRule = collectionRuleMapper.selectById(id);
        if ("1".equals(collectionRule.getRuleType())) {
            try {

                xpathPageProcessor.setId(id);

                Spider.create(xpathPageProcessor)
                        // "网址"开始抓
                        .addUrl(collectionRule.getCollectionPath())
                        .setDownloader(new WebDriverDownloader())
                        .addPipeline(zzPipeline)
                        //开启5个线程抓取
                        .thread(5)
//                        //启动爬虫
                        .start();

            } catch (Exception ex) {
                String info = "抓取" + collectionRule.getCollectionPath() + "数据线程执行异常";
                log.error(info, ex);
                return info;
            }
        } else if ("0".equals(collectionRule.getRuleType())) {
            try {

                zzPageProcessor.setId(id);

                Spider.create(zzPageProcessor)
                        // "网址"开始抓
                        .addUrl(collectionRule.getCollectionPath())
                        .setDownloader(new WebDriverDownloader())
                        .addPipeline(zzPipeline)
                        //开启5个线程抓取
                        .thread(5)
                        //启动爬虫
                        .start();

            } catch (Exception ex) {
                String info = "定时抓取" + collectionRule.getCollectionPath() + "数据线程执行异常";
                log.error(info, ex);
                return info;
            }
        } else {
            return "未设置状态";
        }

        return "开始爬取";
    }

    @Override
    public boolean statusUpdate(Ecms ecms) {
        return ecmsMapper.statusUpdate(ecms);
    }

    @Override
    public Page<Ecms> selectByColumnid(Ecms entity, Page<Ecms> page) {
        QueryWrapper<Ecms> ecmsQueryWrapper = new QueryWrapper<>();
        ecmsQueryWrapper.eq("is_deleted", 0);
        ecmsQueryWrapper.eq("column_status", 1);
        ecmsQueryWrapper.orderByDesc("created_time");
        if (entity.getColumnId() != null) {
            ecmsQueryWrapper.eq("column_id", entity.getColumnId());
        }
        Page<Ecms> ecmsTopic = new Page<>(page.getCurrent(), page.getSize());
        //查询到所有的ecms类，方便查询columnName
        Page<Ecms> ecmsPage = ecmsMapper.selectPage(ecmsTopic, ecmsQueryWrapper);
        //通过ecms的columnid查询栏目名称
        for (int i = 0; i < ecmsPage.getRecords().size(); i++) {
            String columnId = ecmsPage.getRecords().get(i).getColumnId();
            Column column = columnMapper.selectById(columnId);
            if (column != null) {
                ecmsPage.getRecords().get(i).setColumnName(column.getColumnName());
            }
        }
        return ecmsPage;
    }

    /**
     * 审核通过
     *
     * @param ecms
     * @return
     */
    @Override
    public Boolean auditEcms(Ecms ecms) {
        //到这里的是status为1，并且都是id可以显示的,循环遍历
        try {
            if (ecms.getTips() == null) {
                ecmsMapper.updataStatus(ecms.getId(), ecms.getStatus());
            } else {
                ecmsMapper.updateStatusAndTips(ecms);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 审核未通过
     *
     * @param ecms
     */
    @Override
    public Boolean auditFailedEcms(Ecms ecms) {
        try {
            if (ecms.getTips() == null) {
                return ecmsMapper.updataAiledStatus(ecms.getId());
            } else {
                return ecmsMapper.updateAiledStatusAndTips(ecms);
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 批量逻辑删除
     *
     * @param ecmsIds
     * @return
     */
    @Override
    public Boolean logicalDeleted(List<String> ecmsIds) {
        int startNum = 0;
        for (int i = 0; i < ecmsIds.size(); i++) {
            ecmsMapper.update(new Ecms(), new UpdateWrapper<Ecms>().set("is_deleted", 1).eq("id", ecmsIds.get(i)));
            startNum++;
        }
        if (startNum != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 物理批量删除逻辑
     *
     * @param ecmsIds
     * @return
     */
    @Override
    public Boolean removeByReal(List<String> ecmsIds) {
        int startNum = 0;
        for (int i = 0; i < ecmsIds.size(); i++) {
            ecmsMapper.deleteById(ecmsIds.get(i));
            startNum++;
        }
        if (startNum != 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Page<Ecms> sendFocusImgPage(Ecms entity, Page page, String columnId) {
        try {
            Page<Ecms> ecmsTopic = new Page<>(page.getCurrent(), page.getSize());
            QueryWrapper<Ecms> ecmsQueryWrapper = new QueryWrapper<>();
            if (columnId != null) {
                ecmsQueryWrapper.eq("column_id", columnId);
            }
            ecmsQueryWrapper.eq("column_status", 1);
            ecmsQueryWrapper.eq("is_deleted", 0);
            ecmsQueryWrapper.eq("focus_img", 1);
            ecmsQueryWrapper.isNotNull("img");
            Page<Ecms> ecmsPage = ecmsMapper.selectPage(ecmsTopic, ecmsQueryWrapper);

            for (int i = 0; i < ecmsPage.getRecords().size(); i++) {
                Column column = columnMapper.selectOne(new QueryWrapper<Column>()
                        .eq("id", ecmsPage.getRecords().get(i).getColumnId()));
                ecmsPage.getRecords().get(i).setColumnName(column.getColumnName());
            }
            return ecmsPage;

        } catch (NullPointerException e) {
            return null;
        }

    }

    @Override
    public int selectAfterCount(String message) {
        CollectionRule collectionRule = collectionRuleMapper.selectById(message);

        return ecmsMapper.selectCount(new QueryWrapper<Ecms>()
                .eq("column_id", collectionRule.getColumnId()));

    }

    @Override
    public Page<Ecms> selectAll(Ecms entity, Page<Ecms> page, String title) {
        //1、前面都是条件语序
        QueryWrapper<Ecms> ecmsQueryWrapper = new QueryWrapper<Ecms>();
        ecmsQueryWrapper.eq("is_deleted", 0);
        //2、查看是否需要审核通过 approved=true 审核过 false 未审核
        if (entity.getApproved() == null) {
            ecmsQueryWrapper.eq("column_status", 1);
        } else {
            if (!entity.getApproved()) {
                ecmsQueryWrapper.eq("column_status", 0);
            } else {
                //审核通不通过都是显示已审核
                ecmsQueryWrapper.ne("column_status", 0);
            }
        }
        ecmsQueryWrapper.orderByDesc("created_time");
        if (title != null) {
            ecmsQueryWrapper.like("title", title);
        }
        //3、这里会有columnId的传入，需要判断
        if (entity.getColumnId() != null) {
            ecmsQueryWrapper.eq("column_id", entity.getColumnId());
        }
        //进行分页的查询，并且将栏目名称添加到传出的ecms中
        Page<Ecms> ecmsTopic = new Page<>(page.getCurrent(), page.getSize());
        Page<Ecms> getEcms = ecmsMapper.selectPage(ecmsTopic, ecmsQueryWrapper);
        for (int i = 0; i < getEcms.getRecords().size(); i++) {
            Column column = columnMapper.selectOne(new QueryWrapper<Column>()
                    .eq("id", getEcms.getRecords().get(i).getColumnId()));
            getEcms.getRecords().get(i).setColumnName(column.getColumnName());
        }

        return getEcms;
    }

    @Override
    public Page<Ecms> sendFocusImgPageAndC(Ecms entity, Page<Ecms> page, String columnId) {
        try {
            Page<Ecms> ecmsTopic = new Page<>(page.getCurrent(), page.getSize());
            QueryWrapper<Ecms> ecmsQueryWrapper = new QueryWrapper<>();
            ecmsQueryWrapper.eq("column_id", entity.getColumnId());
            ecmsQueryWrapper.eq("is_deleted", 0);
            ecmsQueryWrapper.eq("focus_img", 1);
            Page<Ecms> ecmsPage = ecmsMapper.selectPage(ecmsTopic, ecmsQueryWrapper);
            for (int i = 0; i < ecmsPage.getRecords().size(); i++) {
                Column column = columnMapper.selectOne(new QueryWrapper<Column>()
                        .eq("column_id", ecmsPage.getRecords().get(i).getColumnId()));
                ecmsPage.getRecords().get(i).setColumnName(column.getColumnName());
            }
            return ecmsPage;

        } catch (NullPointerException e) {
            return null;
        }
    }


}
