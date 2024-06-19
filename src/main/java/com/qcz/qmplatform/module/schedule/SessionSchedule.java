package com.qcz.qmplatform.module.schedule;

import com.qcz.qmplatform.common.utils.CacheUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.common.utils.SubjectUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 定时检测会话
 */
@Slf4j
public class SessionSchedule {

    public void run() {
        CacheUtils.SESSION_CACHE.cacheObjIterator().forEachRemaining(cacheObj -> {
            String sessionId = cacheObj.getKey();
            String userId = cacheObj.getValue();
            if (StringUtils.isBlank(userId)) {
                return;
            }

            if (SubjectUtils.isExpire(userId)) {
                SubjectUtils.toLoginPage(sessionId);
            }
        });
    }

}
