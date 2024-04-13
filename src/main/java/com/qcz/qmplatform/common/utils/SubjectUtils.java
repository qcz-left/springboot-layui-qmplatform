package com.qcz.qmplatform.common.utils;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.constant.ResponseCode;
import com.qcz.qmplatform.module.business.system.domain.User;
import com.qcz.qmplatform.module.business.system.service.UserService;
import com.qcz.qmplatform.module.socket.SessionWebSocketServer;
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

    /**
     * 获取当前账号HttpSession ID
     */
    public static String getSessionId() {
        return ServletUtils.getCurrRequest().getSession().getId();
    }

    /**
     * 用户登出
     */
    public static void removeUser() {
        if (StpUtil.isLogin()) {
            Object user = StpUtil.getLoginId();
            StpUtil.logout();
            clearCache();
            log.debug("logout : {}", user);
        }
    }

    /**
     * 清除当前账号缓存
     */
    public static void clearCache() {
        clearCache(getSessionId());
    }

    /**
     * 清除指定会话缓存
     *
     * @param sessionId HttpSession ID
     */
    public static void clearCache(String sessionId) {
        User user = CacheUtils.SESSION_CACHE.get(sessionId);
        CacheUtils.USER_CACHE.remove(user.toString());
        CacheUtils.SESSION_CACHE.remove(sessionId);
    }

    /**
     * 指定账号跳转到登录页
     *
     * @param sessionId HttpSession ID
     */
    public static void toLoginPage(String sessionId) {
        User user = CacheUtils.SESSION_CACHE.get(sessionId);
        ResponseResult<String> responseResult = new ResponseResult<>(ResponseCode.AUTHORIZED_EXPIRE, "会话过期！", user.getUsername());
        log.info("[{}] {}", user.getLoginname(), responseResult);
        clearCache(sessionId);
        SessionWebSocketServer.sendMsg(JSONUtil.toJsonStr(responseResult), sessionId);
    }

    /**
     * 当前账号跳转到登录页
     */
    public static void toLoginPage() {
        SubjectUtils.toLoginPage(SubjectUtils.getSessionId());
    }

    public static String getUserId() {
        return getUser().getId();
    }

    public static String getUserName() {
        return getUser().getUsername();
    }

}
