package com.qcz.qmplatform.module.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpoint;

/**
 * 消息通知
 */
@ServerEndpoint("/socket/notify")
@Component
public class NotifyWebSocketServer extends BaseWebSocketServer {

    protected static final Logger LOGGER = LoggerFactory.getLogger(NotifyWebSocketServer.class);

    public static void sendMsg(String result, String id) {

    }

}
