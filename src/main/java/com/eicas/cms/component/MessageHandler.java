package com.eicas.cms.component;

import com.eicas.cms.crawling.Craw;
import com.eicas.cms.pojo.entity.CollectRule;
import com.eicas.cms.service.ICollectRuleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Getter
@Setter
@Component
public class MessageHandler {

    private Integer code;
    private String data;

    public static ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    private ICollectRuleService iCollectRuleService;

    @Resource
    private Craw craw;

    public void parse(String content, String sessionId) {
        try{
            MessageHandler message  = objectMapper.readValue(content, MessageHandler.class);
            switch (message.getCode()){
                case 0: // 心跳数据
                  break;
                case 1: // 发送爬取请求
                    //获取采集规则id
                    Long id = objectMapper.readValue(message.getData(),Long.class);
                    //获取采集规则
                    CollectRule collectRule = iCollectRuleService.getById(id);
                    //调用爬取方法
                    if(collectRule != null){
                        craw.run(collectRule,sessionId);
                    }
            }
        }catch(Exception e){
            log.error("消息格式错误！"+e.getMessage());
        }
    }


}
