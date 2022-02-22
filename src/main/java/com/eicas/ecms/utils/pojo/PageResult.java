package com.eicas.ecms.utils.pojo;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T>{
    private  int currentPage = 1;
    private long totalPage;
    private long totalNumber;
    private List<T> list;

    public PageResult(PageParam pageParam,long totalNumber,List<T> list){
        this.currentPage = pageParam.getCurrentPage();
        this.totalPage = totalNumber % pageParam.getPageSize() == 0 ? totalNumber
                / pageParam.getPageSize() : totalNumber / pageParam.getPageSize() + 1;
        this.totalNumber = totalNumber;
        this.list = list;
    }

}
