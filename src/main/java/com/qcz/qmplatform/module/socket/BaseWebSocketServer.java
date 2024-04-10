package com.qcz.qmplatform.module.socket;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import org.apache.tomcat.websocket.WsSession;

import java.util.Map;

public abstract class BaseWebSocketServer {

    @OnOpen
    public void onOpen(Session session) {
        getClients().put(getSessionId(session), session);
    }

    @OnClose
    public void onClose(Session session) {
        getClients().remove(getSessionId(session));
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        String sessionId = getSessionId(session);
        if (getClients().get(sessionId) != null) {
            getClients().remove(sessionId);
        }
        throwable.printStackTrace();
    }

    private String getSessionId(Session session) {
        return ((WsSession) session).getHttpSessionId();
    }

    public abstract Map<String, Session> getClients();
}
