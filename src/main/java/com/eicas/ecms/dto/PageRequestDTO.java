package com.eicas.ecms.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageRequestDTO implements Serializable {
    private static final long serialVersionUID = 7835592180535388123L;

    // 当前页面
    protected Integer pageNum = 1;

    // 每页最多显示数量
    protected Integer pageSize = 10;

}
