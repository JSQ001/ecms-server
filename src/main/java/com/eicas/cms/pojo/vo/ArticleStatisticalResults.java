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
    private String allData;
    // 编辑中
    private String editing;
    // 审核通过
    private String approved;
    // 审核不通过
    private String reject;
    //草稿
    private String notApproved;

    //栏目名称
    private String name;
    //栏目ID
    private  long columnId;

    //发布人
    private  String createdName;


    //节点名称
    private  String pointName;



}
