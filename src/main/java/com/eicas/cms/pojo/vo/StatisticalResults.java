package com.eicas.cms.pojo.vo;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticalResults {
    // 全部数据
    private String allData;
    //栏目名称/用户名称/节点名称
    private String name;
    //栏目ID
    private  long columnId;
}
