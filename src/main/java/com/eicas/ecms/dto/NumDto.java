package com.eicas.ecms.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@Accessors(chain = true)
public class NumDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 状态为0的爬取统计
     */
    private Integer status0;
    /**
     * 状态为1的爬取统计
     */
    private Integer status1;
    /**
     * 状态为2的爬取统计
     */
    private Integer status2;
    /**
     * 全部的爬取统计
     */
    private Integer all;
}
