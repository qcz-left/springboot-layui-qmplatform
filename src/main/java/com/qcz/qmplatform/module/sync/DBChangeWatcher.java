package com.qcz.qmplatform.module.sync;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.qcz.qmplatform.common.bean.Observable;
import com.qcz.qmplatform.module.socket.NotifyWebSocketServer;
import com.qcz.qmplatform.module.system.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 数据库记录改变观察者
 */
public class DBChangeWatcher implements Observable {

    @Autowired
    MessageService messageService;

    @Override
    public void receiveMessage(String tableName) {
        switch (tableName) {
            case "sys_message":
                syncMessage();
                break;
            default:
                break;
        }
    }

    /**
     * 同步系统消息
     */
    private void syncMessage() {
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
