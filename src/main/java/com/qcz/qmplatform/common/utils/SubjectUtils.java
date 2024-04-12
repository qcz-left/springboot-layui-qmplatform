package com.qcz.qmplatform.common.utils;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import com.qcz.qmplatform.module.business.system.domain.User;
import com.qcz.qmplatform.module.business.system.service.UserService;
import lombok.extern.slf4j.Slf4j;

/**
 * 当前登录账号工具类
 */
@Slf4j
public class SubjectUtils {

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
        Object principal = StpUtil.getLoginIdDefaultNull();
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
            throw new NotLoginException("用户未登录", "login", "not login");
        }

        return null;
    }

    public static void setUser(User user) {
        CacheUtils.USER_CACHE.put(user.getId(), user);
        CacheUtils.SESSION_CACHE.put(getSessionId(), user);
    }

    public static String getSessionId() {
        return StpUtil.getSession().getId();
    }

    /**
     * 用户登出
     */
    public static void removeUser() {
        if (StpUtil.isLogin()) {
            Object user = StpUtil.getLoginId();
            CacheUtils.USER_CACHE.remove(user.toString());
            CacheUtils.SESSION_CACHE.remove(getSessionId());
            StpUtil.logout();
            log.debug("logout : {}", user);
        }
    }

    public static String getUserId() {
        return getUser().getId();
    }

    public static String getUserName() {
        return getUser().getUsername();
    }

}
