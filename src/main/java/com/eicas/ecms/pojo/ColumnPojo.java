package com.eicas.ecms.pojo;

import com.eicas.ecms.entity.Column;
import lombok.Data;

@Data
public class ColumnPojo extends Column {
    // 类型名称
    private String typeId;
    // 发布日期从
    private String dateFrom;
    // 发布日期至
    private String dateTo;
}
