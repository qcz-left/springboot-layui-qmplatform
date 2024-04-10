package com.qcz.qmplatform.module.socket;

import com.qcz.qmplatform.common.utils.StringUtils;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 会话过期验证，通知页面跳转登录页
 */
@ServerEndpoint("/socket/validateSession")
@Component
public class SessionWebSocketServer extends BaseWebSocketServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionWebSocketServer.class);

    /**
     * 用于存放所有在线客户端
     */
    private static final Map<String, Session> clients = new ConcurrentHashMap<>();

    public static void sendMsg(String result, String id) {
        if (StringUtils.isNotBlank(id) && clients.containsKey(id)) {
            try {
                clients.get(id).getBasicRemote().sendText(result);
            } catch (IOException e) {
                LOGGER.error("[{}]The WebSocket service sent a failed message!", id);
            }
        }
    }

    @Override
    public Map<String, Session> getClients() {
        return clients;
    }

}
