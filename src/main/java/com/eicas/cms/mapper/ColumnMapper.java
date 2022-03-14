package com.eicas.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.pojo.entity.Column;
import com.eicas.cms.pojo.vo.ColumnVO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * <p>
 * 栏目表 Mapper 接口
 * </p>
 *
 * @author jsq
 * @since 2022-03-02
 */
public interface ColumnMapper extends BaseMapper<Column> {

    /**
     *  条件查询栏目树（分页）,匹配条件的同级数据也会查出
     */
    @Select(
        "<script>" +
            "select id, code, parent_id, name, type, path, publish_time, sort_order, keyword, " +
            "custom_url, description from cms_column " +
            "where id in (" +
                "select distinct getRootId(id) from cms_column" +
                    "<where> " +
                    "       and is_deleted = 0 " +
                        "<when test='entity.name != null and entity.name !=\"\"'>" +
                            "and name like CONCAT('%',#{entity.name},'%')" +
                        "</when>"+
                        "<when test='entity.startTime != null'>" +
                            "and publish_time &gt;= #{entity.startTime}" +
                        "</when>"+
                        "<when test='entity.endTime != null'>" +
                            "and publish_time &lt;= #{entity.endTime}" +
                        "</when>"+
                    "</where>" +
            ")" +
        "</script>"
    )
    @Results({
        @Result(column="id", property="id", id=true),
        @Result(property="children", column="id", javaType= List.class,
                many=@Many(select="com.eicas.cms.mapper.ColumnMapper.selectTreeByParentId", fetchType= FetchType.EAGER))
    })
    Page<Column> getTreePage(ColumnVO entity, Page page);

    /**
    *  根据id查询栏目树
    * */
    @Select(
        "<script>" +
            "select id, code, parent_id, name, type, path, publish_time, sort_order, keyword, " +
            "custom_url, description from cms_column" +
            "<where>" +
                "<choose>" +
                    "<when test='id != \"\" and id != null '>" +
                        "and parent_id = #{id}" +
                    "</when>" +
                    "<otherwise>" +
                        "and parent_id is null or parent_id =''" +
                    "</otherwise>" +
                "</choose>" +
            "</where>" +
        "</script>"
    )
    @Results({
        @Result(column="id", property="id", id=true),
        @Result(property="children", column="id",  many=@Many(select="com.eicas.cms.mapper.ColumnMapper.selectTreeByParentId", fetchType= FetchType.EAGER))
    })
    List<Column> selectTreeByParentId(Long id);

    /**
     *  条件查询栏目树
     */
    @Select(
        "<script>" +
            "select id,parent_id,code,name from cms_column " +
                "<where>" +
                    "<if test='entity.deleted != null'>" +
                    "   and is_deleted = #{entity.deleted}" +
                    "</if>" +
                    "<if test='entity.code != null and entity.code!=\"\"'>" +
                    "   and code = #{entity.code, jdbcType=VARCHAR}" +
                    "</if>" +
                    "<when test='entity.name != null and entity.name!=\"\" '>" +
                    "   and name like concat('%',#{entity.name, jdbcType=VARCHAR},'%')" +
                    "</when>" +
                "</where>" +
        "</script>"
    )
    @Results({
        @Result(column="parent_id", property="parentId"),
        @Result(property="parent", column="parent_id", javaType= Column.class, many=@Many(select="com.eicas.cms.mapper.ColumnMapper.getColumnById", fetchType= FetchType.EAGER))
    })
    List<Column> queryByParams(@Param("entity")Column entity);

    /**
     * 通过叶子id查询栏目
     * */

    @Select(
        "select id,parent_id,code,name, parent_id as parentId from cms_column " +
        "where id = #{id}"
    )
    @Results(
        @Result(property="parent", column="parent_id", javaType= Column.class, many=@Many(select="com.eicas.cms.mapper.ColumnMapper.getColumnById", fetchType= FetchType.EAGER))
    )
    List<Column> getColumnById(Long id);

}
