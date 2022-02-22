package com.eicas.ecms.service;

import com.eicas.ecms.entity.CronCrawl;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zyt
 * @since 2022-01-11
 */
public interface ICronCrawlService extends IService<CronCrawl> {

    /**
     * 分页列表
     * @param param 根据需要进行传值
     * @return
     */
    public Page<CronCrawl> page(CronCrawl entity, Page<CronCrawl> page);


    /**
     * 详情
     * @param id
     * @return
     */
    CronCrawl info(Long id);

    /**
    * 新增
    * @param param 根据需要进行传值
    * @return
    */
    void add(CronCrawl param);

    /**
    * 修改
    * @param param 根据需要进行传值
    * @return
    */
    void modify(CronCrawl param);

    /**
    * 删除(单个条目)
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

    Boolean delete();

}
