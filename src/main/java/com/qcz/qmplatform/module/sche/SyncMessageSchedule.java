package com.qcz.qmplatform.module.sche;

import cn.hutool.json.JSONUtil;
import com.qcz.qmplatform.common.utils.SpringContextUtils;
import com.qcz.qmplatform.module.socket.NotifyWebSocketServer;
import com.qcz.qmplatform.module.system.service.MessageService;

import javax.websocket.Session;
import java.util.Collection;

/**
 * 同步系统消息 定时器
 */
public class SyncMessageSchedule {

    public void run() {
        MessageService messageService = SpringContextUtils.getBean(MessageService.class);
        Collection<Session> sessions = NotifyWebSocketServer.getSessions();
        for (Session session : sessions) {
            String userId = session.getUserPrincipal().toString();
            NotifyWebSocketServer.sendMsg(JSONUtil.toJsonStr(messageService.selectNoReadCount(userId)), session);
        }
    }

}
