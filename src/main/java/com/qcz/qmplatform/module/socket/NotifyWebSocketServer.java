package com.qcz.qmplatform.module.socket;

import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息通知
 */
@ServerEndpoint("/socket/notify")
@Component
public class NotifyWebSocketServer extends BaseWebSocketServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyWebSocketServer.class);

    /**
     * 用于存放所有在线客户端
     */
    private static final Map<String, Session> clients = new ConcurrentHashMap<>();

    public static void sendMsg(String result, Session session) {
        try {
            session.getBasicRemote().sendText(result);
        } catch (IOException e) {
            LOGGER.error("[{}]The WebSocket service sent a failed message!", session.getId());
        }
    }

    @Override
    public Map<String, Session> getClients() {
        return clients;
    }

}
