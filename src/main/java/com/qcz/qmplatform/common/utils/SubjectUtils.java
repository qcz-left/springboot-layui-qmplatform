package com.qcz.qmplatform.common.utils;

import cn.hutool.cache.impl.TimedCache;
import com.qcz.qmplatform.module.system.domain.User;
import com.qcz.qmplatform.module.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubjectUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectUtils.class);

    private static final TimedCache<String, User> userCache = CacheUtils.USER_CACHE;

    public static User getUser() {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal != null) {
            String principalStr = principal.toString();
            User user = userCache.get(principalStr);
            if (user == null) {
                user = SpringContextUtils.getBean(UserService.class).getById(String.valueOf(principal));
                setUser(user);
            }
            return user;
        }
        return null;
    }

    public static void setUser(User user) {
        userCache.put(user.getId(), user);
        CacheUtils.SESSION_CACHE.put(getSessionId(), user);
    }

    public static String getSessionId() {
        return SecurityUtils.getSubject().getSession().getId().toString();
    }

    /**
     * 用户登出
     */
    public static void removeUser() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            Object user = subject.getPrincipal();
            userCache.remove(user.toString());
            CacheUtils.SESSION_CACHE.remove(getSessionId());
            subject.logout();
            LOGGER.debug("logout : {}", user);
        }
    }

    public static String getUserId() {
        return getUser().getId();
    }

    public static String getUserName() {
        return getUser().getUsername();
    }

}
