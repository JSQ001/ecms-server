package com.eicas.cms.component;

import com.alibaba.fastjson.JSONArray;
import com.eicas.cms.pojo.vo.WebSocketResponseToClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author jsq
 * @since 2022-03-05
 **/
@ServerEndpoint("/api/article/crawling")
@Component
@Slf4j
public class MyWebsocketServer {
    /**
     * 存放所有在线的客户端
     */
    public static Map<String, Session> clients = new HashMap<>();

    public static Map<String, Integer> pageQueue = new HashMap<>();

    private MessageHandler messageHandler;

    @OnOpen
    public void onOpen(Session session) {
        log.info("有新的客户端连接了: {}", session.getId());
        //将新用户存入在线的组
        MyWebsocketServer.clients.put(session.getId(), session);
    }

    /**
     * 客户端关闭
     * @param session session
     */
    @OnClose
    public void onClose(Session session) {
        log.info("有用户断开了, id为:{}", session.getId());
        //将掉线的用户移除在线的组里
        MyWebsocketServer.clients.remove(session.getId());
    }

    /**
     * 发生错误
     * @param throwable e
     */
    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    /**
     * 收到客户端发来消息
     * @param message  消息对象
     */
    @OnMessage
    public void onMessage(String message,Session session) {
        log.info("服务端收到客户端发来的消息: {}", message);
        messageHandler = SpringCtxUtils.getBean(MessageHandler.class);
        messageHandler.parse(message,session.getId());
        //this.sendAll(message);
    }

    /**
     * 群发消息
     * @param message 消息内容
     */
    private void sendAll(WebSocketResponseToClient message) {
        for (Map.Entry<String, Session> item : MyWebsocketServer.clients.entrySet()) {
            sendMessage(item.getValue().getId(), message);
        }
    }
    public static void sendMessage(String sessionId, WebSocketResponseToClient message){
        Session session = MyWebsocketServer.clients.get(sessionId);
        session.getAsyncRemote().sendText(JSONArray.toJSON(message).toString());
    }
}
