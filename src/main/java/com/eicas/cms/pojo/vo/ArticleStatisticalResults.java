package com.eicas.cms.pojo.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 文章统计结果类
 * */

@Getter
@Setter
public class ArticleStatisticalResults {

    // 全部数据
    private String all;
    // 编辑中
    private String editing;
    // 审核通过
    private String approved;
    // 审核不通过
    private String reject;

    private String notApproved;

    private String name;

}
