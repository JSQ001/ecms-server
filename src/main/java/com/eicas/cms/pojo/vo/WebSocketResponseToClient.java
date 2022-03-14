package com.eicas.cms.pojo.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 手动采集，websocket发送给client端的数据类
 * */

@Getter
@Setter
public class WebSocketResponseToClient {

    private Integer code;
    private String message;

    public WebSocketResponseToClient(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}