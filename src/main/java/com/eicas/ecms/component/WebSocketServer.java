package com.eicas.ecms.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eicas.ecms.pojo.SessionMapPOJO;
import com.eicas.ecms.processor.XpathPageProcessor;
import com.eicas.ecms.service.IEcmsService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.apache.tomcat.jni.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint(value = "/api/notice/ecmsState")
@Component
public class WebSocketServer {

    public static IEcmsService iEcmsService;

    @Autowired
    private void setIEcmsService(IEcmsService iEcmsService) {
        WebSocketServer.iEcmsService = iEcmsService;
    }


    @PostConstruct
    public void init() {
        System.out.println("websocket 加载");
    }

    private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    private static final AtomicInteger OnlineCount = new AtomicInteger(0);
    // concurrent包的线程安全Set，用来存放每个客户端对应的Session对象。
    private static CopyOnWriteArraySet<Session> SessionSet = new CopyOnWriteArraySet<Session>();


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {

        SessionSet.add(session);
        int cnt = OnlineCount.incrementAndGet(); // 在线数加1
        log.info("有连接加入，当前连接数为：{}", cnt);
        log.info("当前sessionid为-----------------》{}", session.getId());
        SendMessage(session, "连接成功");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        SessionSet.remove(session);
        SessionMapPOJO.getInstance().getMp().remove(session.getId());
        int cnt = OnlineCount.decrementAndGet();
        log.info("有连接关闭，当前连接数为：{}", cnt);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        ConcurrentHashMap<String, String> mp = SessionMapPOJO.getInstance().getMp();
        log.info("来自客户端的消息：{}", message);
//        JSONObject jsonObject = JSON.parseObject(message);
        SendMessage(session, "收到消息，消息内容：" + message);
        int beforeCount = iEcmsService.selectAfterCount(message);
        //把message-->id保存到mp中
        mp.put(session.getId(), message);
        //调用service层方法
        iEcmsService.getQ(message);


        //新建一个线程，用来查找是否有新数据在里面，有的话就发送消息
        int a = 1;
        for (int i = 0; i < 10; i++) {

            int afterCount = iEcmsService.selectAfterCount(message);
            if (afterCount > beforeCount) {
                SendMessage(session, "200");
                log.info("发送成功");
                break;
            }
            a++;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.info("第" + i + "次======================================================================================================================");
        }
        //走到这里意味着没有采集
        if (a == 11) {
            SendMessage(session, "500");
        } else {
            SendMessage(session, "已经结束了");
        }

    }

    /**
     * 出现错误
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误：{}，Session ID： {}", error.getMessage(), session.getId());
        error.printStackTrace();
    }

    /**
     * 通过这个往前端发送消息
     * 发送消息，实践表明，每次浏览器刷新，session会发生变化。
     *
     * @param session
     * @param message
     */
    public static void SendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(String.format(message));
        } catch (IOException e) {
            log.error("发送消息出错：{}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 群发消息
     *
     * @param message
     * @throws IOException
     */
    public static void BroadCastInfo(String message) throws IOException {
        for (Session session : SessionSet) {
            if (session.isOpen()) {
                SendMessage(session, message);
            }
        }
    }

    /**
     * 指定Session发送消息
     *
     * @param sessionId
     * @param message
     * @throws IOException
     */
    public static void SendMessage(String message, String sessionId) throws IOException {
        Session session = null;
        for (Session s : SessionSet) {
            if (s.getId().equals(sessionId)) {
                session = s;
                break;
            }
        }


        if (session != null) {

            SendMessage(session, message);


        } else {
            log.warn("没有找到你指定ID的会话：{}", sessionId);
        }
    }


    class CheckCount implements Callable<Integer> {
        private String sendId;

        private CheckCount() {

        }

        public CheckCount(String sendId) {
            this.sendId = sendId;
        }

        @Override
        public Integer call() throws Exception {
            return iEcmsService.selectAfterCount(sendId);
        }
    }


}
