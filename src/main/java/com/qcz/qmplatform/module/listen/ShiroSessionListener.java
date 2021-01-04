package com.qcz.qmplatform.module.listen;

import cn.hutool.json.JSONUtil;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.constant.ResponseCode;
import com.qcz.qmplatform.module.socket.SessionWebSocketServer;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 配置session监听器,
 */
public class ShiroSessionListener implements SessionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroSessionListener.class);

    /**
     * 会话创建时触发
     */
    @Override
    public void onStart(Session session) {

    }

    /**
     * 退出会话时触发
     */
    @Override
    public void onStop(Session session) {

    }

    /**
     * 会话过期时触发
     */
    @Override
    public void onExpiration(Session session) {
        ResponseResult<?> responseResult = new ResponseResult<>(ResponseCode.UNAUTHORIZED, "会话过期！", null);
        LOGGER.debug("{}", responseResult);
        SessionWebSocketServer.sendMsg(JSONUtil.toJsonStr(responseResult), session.getId().toString());
    }
}


