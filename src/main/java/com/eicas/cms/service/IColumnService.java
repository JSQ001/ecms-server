package com.eicas.cms.service;

import com.eicas.cms.pojo.entity.Column;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.pojo.vo.ColumnVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 栏目表 服务类
 * </p>
 *
 * @author jsq
 * @since 2022-03-04
 */
public interface IColumnService extends IService<Column> {

    /**
     * 栏目表分页列表
     * @param  columnVo
     * @return
     */
    Page<Column> listColumns(ColumnVO columnVo);

    /**
     * 查询栏目树
     * @param param 栏目id
     * @return
     */
    List<Column> getColumnTree(Long id);

    /**
     * 根据id物理删除
     * @param param 栏目id
     * @return
     */
    boolean logicalDeleteById(Long id);

    /**
     * 新建或更新
     * @param entity 栏目id
     * @return
     */
    boolean createOrUpdate(Column entity);

    /**
     * 根据父栏目code查询子栏目列表
     * @param code
     * @return
     */
    List<Column> listByParentCode(String code);

    /**
     * 根据父栏目id查询叶子栏目
     * @param id
     * @return
     */
    List<Long> listIdsByParentId(Long id);


    /**
     *栏目移动
     * @param目标栏目id ,目标栏目上级id,目标栏目层级关秀
     * @return
     * */
    int MoveColumn(Map columnentity);


}
