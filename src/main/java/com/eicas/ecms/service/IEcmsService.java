package com.eicas.ecms.service;

import com.eicas.ecms.bo.ListAuditBo;
import com.eicas.ecms.entity.Ecms;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author jsq
 * @since 2021-10-28
 */
public interface IEcmsService extends IService<Ecms> {

    /**
     * 文章表分页列表
     * @param entity 根据需要进行传值
     * @return
     */
    public Page<Ecms> page(Ecms entity, Page<Ecms> page);
    /**
     * 文章表详情
     * @param id
     * @return
     */
    Ecms info(Long id);

    /**
    * 文章表新增
    * @param param 根据需要进行传值
    * @return
    */
    void add(Ecms param);

    /**
    * 文章表修改
    * @param param 根据需要进行传值
    * @return
    */
    void modify(Ecms param);

    /**
    * 文章表删除(单个条目)
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

    /**
     * 根据文章状态查询文章
     * @param status 文章状态：0未审核；1审核通过；2审核未通过
     * @param page 分页
     * @return
     */
    Page<Ecms> getPageByStatus(Integer status, Page<Ecms> page);

    /**
     * 获取文章
     * @param id 模块id
     */
    String getQ(String id);

    boolean statusUpdate(Ecms ecms);


    Page<Ecms> selectByColumnid(Ecms entity, Page<Ecms> page);

    Boolean auditEcms(Ecms ecms);

    Page<Ecms> sendFocusImgPage(Ecms entity,Page page,String columnId);

    int selectAfterCount(String message);


    Page<Ecms> selectAll(Ecms entity, Page<Ecms> page,String title);

    Page<Ecms> sendFocusImgPageAndC(Ecms entity, Page<Ecms> page, String columnId);

    Boolean auditFailedEcms(Ecms ecms);

    Boolean logicalDeleted(List<String> ecmsIds);

    Boolean removeByReal(List<String> ecmsIds);
}
