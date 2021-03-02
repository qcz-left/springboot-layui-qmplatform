package com.qcz.qmplatform.module.socket;

import com.qcz.qmplatform.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * 会话过期验证，通知页面跳转登录页
 */
@ServerEndpoint("/socket/validateSession")
@Component
public class SessionWebSocketServer extends BaseWebSocketServer {

    protected static final Logger LOGGER = LoggerFactory.getLogger(SessionWebSocketServer.class);

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
