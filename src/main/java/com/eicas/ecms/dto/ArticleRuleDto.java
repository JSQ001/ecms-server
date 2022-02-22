package com.eicas.ecms.dto;

import com.eicas.ecms.entity.Ecms;
import liquibase.pro.packaged.E;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;


/**
 * <p>
 * 文章规则表
 * </p>
 *
 * @author jsq
 * @since 2021-10-28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ArticleRuleDto extends PageRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 栏目id
     */
    private String columnId;

    /**
     * 文章id
     */
    private String articleId;

    /**e
     * 规则id
     */
    private String ruleId;

    /**
     * 下载图片数量
     */
    private Integer imgNum;

    /**
     * 创建时间
     */
    private String createdTime;

    /**
     * 更新时间
     */
    private String updatedTime;

    /**
     * 创建者
     */
    private String createdBy;

    /**
     * 更新者
     */
    private String updatedBy;

    /**
     * 是否删除
     */
    private Boolean deleted;

    /**
     * 爬取文章信息
     */
    private Ecms Ecms;


    private List<Ecms> ecmsList;

    /**
     * 状态：0，待审核；1，审核通过；2，审核不通过
     */
    private String status;

    /**
     * 查看类型
     */
    private String type;

    /**
     * 搜索开始时间
     */
    private String start;

    /**
     * 搜索结束时间
     */
    private String end;
}
