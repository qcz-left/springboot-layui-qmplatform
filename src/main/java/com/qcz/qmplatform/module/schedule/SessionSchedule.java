package com.qcz.qmplatform.module.schedule;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.constant.ResponseCode;
import com.qcz.qmplatform.common.utils.CacheUtils;
import com.qcz.qmplatform.module.business.system.domain.User;
import com.qcz.qmplatform.module.socket.SessionWebSocketServer;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 定时检测会话
 */
@Slf4j
public class SessionSchedule {

    public void run() {
        CacheUtils.SESSION_CACHE.cacheObjIterator().forEachRemaining(cacheObj -> {
            String sessionId = cacheObj.getKey();
            User user = cacheObj.getValue();
            if (Objects.isNull(user)) {
                return;
            }

            if (!StpUtil.isLogin(user.getId())) {
                ResponseResult<String> responseResult = new ResponseResult<>(ResponseCode.AUTHORIZED_EXPIRE, "会话过期！", user.getUsername());
                log.info("[{}] {}", user.getLoginname(), responseResult);
                CacheUtils.SESSION_CACHE.remove(sessionId);
                SessionWebSocketServer.sendMsg(JSONUtil.toJsonStr(responseResult), sessionId);
            }
        });
    }

}
