package com.eicas.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.entity.CatalogEntity;
import com.eicas.cms.pojo.param.ArticleStaticsResult;
import com.eicas.cms.pojo.param.CatalogArticleStaticsResult;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 栏目信息表 Mapper 接口
 *
 * @author osnudt
 * @since 2022-04-19
 */
public interface CatalogMapper extends BaseMapper<CatalogEntity> {


    /**
     * 根据id获取子级节点最大treeRel
     * */
    @Select("<script>" +
            "select ifnull((" +
            "   select Max(tree_rel) treeRel from cms_catalog " +
            "   <where>" +
            "       <choose>" +
            "           <when test='id != \"\" and id != null '>" +
            "               and parent_id = #{id}" +
            "           </when>" +
            "       <otherwise>" +
            "           and parent_id = 0" +
            "       </otherwise>" +
            "       </choose>" +
            "   </where>" +
            "),0) as treeRel\n" +
            "</script>")
    String getMaxTreeRelByParentId(Long id);

    /**
     * 查询栏目树
     * */
    @Select(
            "<script>" +
                    "select id, code, tree_rel, parent_id, catalog_name, cover_img_url, flag, content_template_url," +
                    "   is_allowed_submit as allowed_submit, is_modify as modify, is_visible as visible, remarks,type, path, publish_time, sort_order, keyword, " +
                    "   custom_url from cms_catalog" +
                    "<where>" +
                    "   and is_deleted = 0 " +
                    "   <choose>" +
                    "       <when test='id != \"\" and id != null '>" +
                    "           and parent_id = #{id}" +
                    "       </when>" +
                    "   <otherwise>" +
                    "       and parent_id = 0" +
                    "   </otherwise>" +
                    "   </choose>" +
                    "</where>" +
                    "order by sort_order, publish_time desc" +
                    "</script>"
    )
    @Results({
            @Result(column="id", property="id", id=true),
            @Result(property="children", column="id",  many=@Many(select="com.eicas.cms.mapper.CatalogMapper.listCatalogTreeByParentId", fetchType= FetchType.EAGER))
    })
    List<CatalogEntity> listCatalogTreeByParentId(Long id);



    /**
     * 根据code查询下级栏目
     * @param code 栏目code
     * @return List<CatalogEntity>
     */

    @Select("" +
            "<script>" +
            "   select cms_catalog.id, code, tree_rel, parent_id, catalog_name, cover_img_url, flag, content_template_url," +
            "   is_allowed_submit as allowed_submit, is_modify as modify, is_visible as visible, remarks,type, path, publish_time, sort_order, keyword, custom_url " +
            "   from cms_catalog, (select id from cms_catalog where code = #{code}) t" +
            "   <where>" +
            "       and is_deleted = 0 " +
            "       and cms_catalog.parent_id = t.id" +
            "   </where>" +
            "   order by sort_order" +
            "</script>"
    )
    List<CatalogEntity> listChildrenWithCode(String code);
}
