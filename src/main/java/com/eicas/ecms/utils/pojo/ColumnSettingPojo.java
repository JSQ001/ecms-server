package com.eicas.ecms.utils.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColumnSettingPojo {
    // 类型名称
    private String typeId;
    // 发布日期从
    private String dateFrom;
    // 发布日期至
    private String dateTo;
    // 栏目名称
    private String name;

}
