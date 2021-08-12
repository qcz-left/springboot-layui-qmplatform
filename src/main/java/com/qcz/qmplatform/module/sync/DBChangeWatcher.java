package com.qcz.qmplatform.module.sync;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.qcz.qmplatform.common.bean.DBProperties;
import com.qcz.qmplatform.common.bean.Observable;
import com.qcz.qmplatform.common.utils.SpringContextUtils;
import com.qcz.qmplatform.module.socket.NotifyWebSocketServer;
import com.qcz.qmplatform.module.system.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 数据库记录改变观察者
 */
public class DBChangeWatcher implements Observable {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBChangeWatcher.class);

    @Override
    public void receiveMessage(Object tableName) {
        LOGGER.debug("A database notification was received: " + tableName);

        switch ((String) tableName) {
            case DBProperties.Table.SYS_MESSAGE:
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
            MessageService messageService = SpringContextUtils.getBean(MessageService.class);
            Map<String, Map<String, Long>> noReadCount = messageService.selectNoReadCount(userIds);
            for (Session session : sessions) {
                String userId = session.getUserPrincipal().toString();
                NotifyWebSocketServer.sendMsg(JSONUtil.toJsonStr(noReadCount.get(userId)), session);
            }
        }
    }

    static {
        DBChangeCenter.getInstance().addObserver(new DBChangeWatcher());
    }
}
