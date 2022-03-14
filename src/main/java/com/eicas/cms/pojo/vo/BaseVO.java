package com.eicas.cms.pojo.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseVO<T> {

    @ApiModelProperty("页码")
    private Integer current;
    @ApiModelProperty("页码大小")
    private Integer size;

    public Page<T> pageFactory(){
        Integer current = this.getCurrent();
        Integer size = this.getSize();
        if(current == null){
            current = 0;
        }
        if(size == null){
            size = 0;
        }
        return new Page<T>(current,size);
    }
}
