package com.eicas.ecms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eicas.ecms.dto.NumDto;
import com.eicas.ecms.entity.Column;
import com.eicas.ecms.entity.Ecms;
import com.eicas.ecms.entity.ArticleRule;
import com.eicas.ecms.mapper.ArticleRuleMapper;
import com.eicas.ecms.mapper.ColumnMapper;
import com.eicas.ecms.service.IArticleRuleService;
import com.eicas.ecms.dto.ArticleRuleDto;
import com.eicas.ecms.mapper.EcmsMapper;
import com.eicas.ecms.utils.StringDateUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class ArticleRuleServiceImpl extends ServiceImpl<EcmsMapper, Ecms> implements IArticleRuleService {

    @Resource
    private ArticleRuleMapper articleRuleMapper;
    @Resource
    private EcmsMapper ecmsMapper;
    @Autowired
    private ColumnMapper columnMapper;



    @Override
    public Ecms info(Long id) {
        return null;
    }

    @Override
    public void add(ArticleRule param) {
        param.setCreatedTime(StringDateUtils.Now());
        articleRuleMapper.insert(param);
    }

    @Override
    public void modify(ArticleRule param) {

        articleRuleMapper.updateById(param);
    }

    @Override
    public void remove(Long id) {
        articleRuleMapper.deleteById(id);
    }

    @Override
    public void removes(List<Long> ids) {

    }


    @Override
    public PageInfo<ArticleRuleDto> page(ArticleRuleDto articleRuleDto) {
        PageHelper.startPage(articleRuleDto);
        List<ArticleRule> articleRules = articleRuleMapper.selectColumnId(articleRuleDto);
        List<ArticleRuleDto> articleRuleDtos = new ArrayList<>();
        for (ArticleRule articleRule : articleRules) {
            articleRuleDto.setImgNum(articleRule.getImgNum());
            articleRuleDto.setCreatedTime(articleRule.getCreatedTime());
            articleRuleDto.setEcms(ecmsMapper.selectById(articleRule.getArticleId()));
            articleRuleDto.setColumnId(articleRule.getColumnId());
            articleRuleDtos.add(articleRuleDto);
        }
        return new PageInfo<>(articleRuleDtos);
    }

    @Override
    public NumDto selectNum(ArticleRuleDto articleRuleDto) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        //按日期格式统计采集数据
        if (StringUtils.isBlank(articleRuleDto.getType())) {
            return Num(articleRuleDto);
        }
        switch (articleRuleDto.getType()) {
            case "year":
                calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 360);
                articleRuleDto.setStart(df.format(calendar.getTime()));

                break;
            case "month":
                calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 30);
                articleRuleDto.setStart(df.format(calendar.getTime()));

                break;
            case "week":
                calendar.setTime(new Date());
                calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 7);
                articleRuleDto.setStart(df.format(calendar.getTime()));
                break;
        }

        return Num(articleRuleDto);
    }

    @Override
    public Page<Ecms> getEntityMsg(ArticleRuleDto articleRuleDto, Page<Ecms> page) {
        if (articleRuleDto.getStatus().equals(0)) {
            QueryWrapper<ArticleRule> articleRuleQueryWrapper = new QueryWrapper<>();
            articleRuleQueryWrapper.eq("rule_id", articleRuleDto.getRuleId());
            List<ArticleRule> articleRules = articleRuleMapper.selectList(articleRuleQueryWrapper);
            //得到Column_id
            Page<Ecms> ecmsPage = new Page<>();
            for (int i = 0; i < articleRules.size(); i++) {
                List<Ecms> ecms = ecmsMapper.selectList(new QueryWrapper<Ecms>().eq("column_id", articleRules.get(i).getColumnId()).eq("is_deleted", 0).eq("column_status", 0));
                for (int j = 0; j < ecms.size(); j++) {
                    String columnId = ecms.get(j).getColumnId();
                    Column column = columnMapper.selectById(columnId);
                    if (column != null) {
                        ecms.get(j).setColumnName(column.getColumnName());
                    }
                }
                ecmsPage.setRecords(ecms);
                ecmsPage.setTotal(ecms.size());
            }
            ecmsPage.setCurrent(articleRuleDto.getPageNum());
            ecmsPage.setSize(articleRuleDto.getPageSize());

            return ecmsPage;
        } else {
            QueryWrapper<ArticleRule> articleRuleQueryWrapper = new QueryWrapper<>();
            articleRuleQueryWrapper.eq("rule_id", articleRuleDto.getRuleId());
            List<ArticleRule> articleRules = articleRuleMapper.selectList(articleRuleQueryWrapper);
            //得到Column_id

            Page<Ecms> ecmsPage = new Page<>();
            for (int i = 0; i < articleRules.size(); i++) {

                List<Ecms> ecms = ecmsMapper.selectList(new QueryWrapper<Ecms>().eq("column_id", articleRules.get(i).getColumnId()).eq("is_deleted", 0).ne("column_status", 0));

                for (int j = 0; j < ecms.size(); j++) {
                    String columnId = ecms.get(j).getColumnId();
                    Column column = columnMapper.selectById(columnId);
                    if (column != null) {
                        ecms.get(j).setColumnName(column.getColumnName());
                    }
                }
                ecmsPage.setRecords(ecms);
                ecmsPage.setTotal(ecms.size());

            }
            ecmsPage.setCurrent(articleRuleDto.getPageNum());
            ecmsPage.setSize(articleRuleDto.getPageSize());
            return ecmsPage;
        }

    }


    public NumDto Num(ArticleRuleDto articleRuleDto) {
        NumDto numDto = new NumDto();
        numDto.setAll(articleRuleMapper.selectNum(articleRuleDto));
        numDto.setStatus0(articleRuleMapper.selectNum(articleRuleDto.setStatus("0")));
        numDto.setStatus1(articleRuleMapper.selectNum(articleRuleDto.setStatus("1")));
        numDto.setStatus2(articleRuleMapper.selectNum(articleRuleDto.setStatus("2")));
        return numDto;
    }


    public Page<Ecms> getEntityClsMsg(ArticleRuleDto articleRuleDto, Page<Ecms> page) {
        Integer start = Math.toIntExact((page.getCurrent() - 1) * page.getSize());
        Integer end = Math.toIntExact(page.getSize());
        //1、分页查询
        List<Ecms> ecmsList = ecmsMapper.selectAuditStatus(articleRuleDto, end, start);
        //2、添加栏目名称
        String columnName=columnMapper.selectColumnName(articleRuleDto);
        for (int i = 0; i < ecmsList.size(); i++) {
            ecmsList.get(i).setColumnName(columnName);
        }
        Long count = ecmsMapper.selectCountMyself(articleRuleDto);
        page.setTotal(count);
        page.setRecords(ecmsList);
        return page;

    }

}
