package com.qcz.qmplatform.module.socket;

import org.apache.tomcat.websocket.WsSession;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseWebSocketServer {

    /**
     * 用于存放所有在线客户端
     */
    static final Map<String, Session> clients = new ConcurrentHashMap<>();

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

}
