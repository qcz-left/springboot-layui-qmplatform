package com.qcz.qmplatform.module.socket;

import com.qcz.qmplatform.common.utils.StringUtils;
import org.apache.tomcat.websocket.WsSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/socket/validateSession")
@Component
public class SessionWebSocketServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionWebSocketServer.class);

    /**
     * 用于存放所有在线客户端
     */
    private static final Map<String, Session> clients = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        clients.put(getSessionId(session), session);
    }

    @OnClose
    public void onClose(Session session) {
        clients.remove(getSessionId(session));
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        String sessionId = getSessionId(session);
        if (clients.get(sessionId) != null) {
            clients.remove(sessionId);
        }
        throwable.printStackTrace();
    }

    private String getSessionId(Session session) {
        return ((WsSession) session).getHttpSessionId();
    }

    public static void sendMsg(String result, String id) {
        if (StringUtils.isNotBlank(id) && clients.containsKey(id)) {
            try {
                clients.get(id).getBasicRemote().sendText(result);
            } catch (IOException e) {
                LOGGER.error("[{}]The WebSocket service sent a failed message!", id);
            }
        }
    }
}
