package com.eicas.ecms.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.fillStrategy(metaObject, "createdTime", LocalDateTime.now());
        this.fillStrategy(metaObject, "updatedTime", LocalDateTime.now());
        this.fillStrategy(metaObject, "createdBy","admin");
        this.fillStrategy(metaObject, "updatedBy","admin");
        this.fillStrategy(metaObject, "password","123456");
        this.fillStrategy(metaObject, "deleted",false);
        this.fillStrategy(metaObject, "isDeleted",0);
        this.fillStrategy(metaObject, "focusImg",false);
        this.fillStrategy(metaObject, "status",0);
        System.out.println(metaObject.getObjectWrapper());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updatedTime", () -> LocalDateTime.now(), LocalDateTime.class);
        this.fillStrategy(metaObject, "updatedBy", "admin");
    }
}
