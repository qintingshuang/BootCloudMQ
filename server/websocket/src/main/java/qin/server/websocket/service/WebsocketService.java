package qin.server.websocket.service;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.ws.rs.Path;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qintingshuang
 * @create 2020-08-11 14:37
 * @description WebsocketService
 * 地址
 **/
@Component
@ServerEndpoint(value = "/websocket/{id}")
public class WebsocketService {

    private static AtomicInteger count = new AtomicInteger(0);
    private static ConcurrentHashMap<String, WebsocketService> websocketSet = new ConcurrentHashMap<>(12);
    private Session session;
    private String id = "";


    @OnOpen
    public void onOpen(@PathParam(value = "id") String id, Session session) throws IOException {
        this.session = session;
        this.id = id;
        websocketSet.put(id, this);
        System.err.println("用户" + id + "加入！当前在线人数为" + count.incrementAndGet());
        sendMessage("连接成功");
    }


    @OnClose
    public void onClose() {
        websocketSet.remove(this);
        System.err.println("有一连接关闭！当前在线人数为" + count.decrementAndGet());
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {

        System.err.println("来自客户端的消息:" + message);
        String sendMessage = message.split(",")[0];
        String sendUserId = message.split(",")[1];

        if (sendUserId.equals("0")) {
            sendToAll(sendMessage);
        } else {
            sendToUser(sendMessage, sendUserId);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.err.println("发生错误");
        error.printStackTrace();
    }


    public void sendToUser(String message, String sendUserId) throws IOException {
        if (!Objects.isNull(websocketSet.get(sendUserId))) {
            if (this.id.equals(sendUserId)) {
                websocketSet.get(sendUserId).sendMessage(message);
            } else {
                websocketSet.get(sendUserId).sendMessage("用户" + id + "发来消息：" + " <br/> " + message);
            }
        }
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);

    }

    public void sendToAll(String message) throws IOException {
        for (String key : websocketSet.keySet()) {
            websocketSet.get(key).sendMessage(message);
        }
    }

}



