package com.eicas.ecms.utils.pojo;

public class PageParam {
    private int beginLine;// 当前行
    private Integer pageSize = 3;
    private Integer currentPage = 0;// 从0开始

    public int getBeginLine() {
        return pageSize * currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
