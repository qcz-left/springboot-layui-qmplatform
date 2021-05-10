package com.qcz.qmplatform.module.sche;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.qcz.qmplatform.common.utils.SpringContextUtils;
import com.qcz.qmplatform.module.socket.NotifyWebSocketServer;
import com.qcz.qmplatform.module.system.service.MessageService;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 同步系统消息 定时器
 */
public class SyncMessageSchedule {

    public void run() {
        MessageService messageService = SpringContextUtils.getBean(MessageService.class);
        Collection<Session> sessions = NotifyWebSocketServer.getSessions();
        if (!CollectionUtil.isEmpty(sessions)) {
            List<String> userIds = new ArrayList<>();
            for (Session session : sessions) {
                userIds.add(session.getUserPrincipal().toString());
            }
            Map<String, Map<String, Long>> noReadCount = messageService.selectNoReadCount(userIds);
            for (Session session : sessions) {
                String userId = session.getUserPrincipal().toString();
                NotifyWebSocketServer.sendMsg(JSONUtil.toJsonStr(noReadCount.get(userId)), session);
            }
        }
    }

}
