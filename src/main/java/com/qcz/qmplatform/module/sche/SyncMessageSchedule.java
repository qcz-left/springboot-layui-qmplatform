package com.qcz.qmplatform.module.sche;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qcz.qmplatform.common.utils.SpringContextUtils;
import com.qcz.qmplatform.module.socket.NotifyWebSocketServer;
import com.qcz.qmplatform.module.system.domain.Message;
import com.qcz.qmplatform.module.system.service.MessageService;

import javax.websocket.Session;
import java.util.Collection;

public class SyncMessageSchedule {

    public void run() {
        MessageService messageService = SpringContextUtils.getBean(MessageService.class);
        Collection<Session> sessions = NotifyWebSocketServer.getSessions();
        System.err.println(sessions);
        for (Session session : sessions) {
            String userId = session.getUserPrincipal().toString();
            QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("read", 0);
            queryWrapper.eq("receiver", userId);
            queryWrapper.groupBy("type");
            queryWrapper.select("type", "count(type)");
            NotifyWebSocketServer.sendMsg(JSONUtil.toJsonStr(messageService.listMaps(queryWrapper)), session);
        }
    }

}
