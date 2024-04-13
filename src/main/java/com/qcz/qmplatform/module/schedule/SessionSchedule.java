package com.qcz.qmplatform.module.schedule;

import cn.dev33.satoken.stp.StpUtil;
import com.qcz.qmplatform.common.utils.CacheUtils;
import com.qcz.qmplatform.common.utils.SubjectUtils;
import com.qcz.qmplatform.module.business.system.domain.User;
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
                SubjectUtils.toLoginPage(sessionId);
            }
        });
    }

}
