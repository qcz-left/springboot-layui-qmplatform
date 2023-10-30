package com.qcz.qmplatform.common.utils;

import com.qcz.qmplatform.common.exception.NotLoginException;
import com.qcz.qmplatform.module.business.system.domain.User;
import com.qcz.qmplatform.module.business.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 当前登录账号工具类
 */
public class SubjectUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectUtils.class);

    /**
     * 获取当前用户（未获取到会抛出异常）
     */
    public static User getUser() {
        return getUser(true);
    }

    /**
     * 获取当前用户，未获取到抛出异常
     *
     * @param throwException 是否抛出异常
     */
    public static User getUser(boolean throwException) {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal != null) {
            String principalStr = principal.toString();
            User user = CacheUtils.USER_CACHE.get(principalStr);
            if (user == null) {
                user = SpringContextUtils.getBean(UserService.class).getById(principalStr);
                setUser(user);
            }
            return user;
        }

        if (throwException) {
            throw new NotLoginException("用户未登录");
        }

        return null;
    }

    public static void setUser(User user) {
        CacheUtils.USER_CACHE.put(user.getId(), user);
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
            CacheUtils.USER_CACHE.remove(user.toString());
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
