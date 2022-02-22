package com.eicas.ecms.pojo;

import lombok.Data;

import java.util.concurrent.ConcurrentHashMap;

@Data
public class SessionMapPOJO {
    private ConcurrentHashMap<String, String> mp;
    private static SessionMapPOJO POJO = new SessionMapPOJO();

    private SessionMapPOJO() {
        this.mp = new ConcurrentHashMap<>();
    }

    public static SessionMapPOJO getInstance() {
        return POJO;
    }

}
