package com.qcz.qmplatform.common.utils;

import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.qcz.qmplatform.common.bean.LoginUser;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.constant.ResponseCode;
import com.qcz.qmplatform.module.business.system.domain.User;
import com.qcz.qmplatform.module.business.system.service.UserService;
import com.qcz.qmplatform.module.socket.SessionWebSocketServer;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 当前登录账号工具类
 */
@Slf4j
public class SubjectUtils {

    /**
     * 获取当前用户（未获取到会抛出异常）
     */
    public static LoginUser getUser() {
        return getUser(true);
    }

    /**
     * 获取当前用户，未获取到抛出异常
     *
     * @param throwException 是否抛出异常
     */
    public static LoginUser getUser(boolean throwException) {
        Object principal = StpUtil.getLoginIdDefaultNull();
        if (principal != null) {
            String principalStr = principal.toString();
            LoginUser user = CacheUtils.USER_CACHE.get(principalStr);
            if (user == null) {
                user = setUser(SpringContextUtils.getBean(UserService.class).getById(principalStr));
            }
            return user;
        }

        if (throwException) {
            throw new NotLoginException("用户未登录", "login", "not login");
        }

        return null;
    }

    public static LoginUser setUser(User user) {
        LoginUser loginUser = new LoginUser();
        BeanUtil.copyProperties(user, loginUser);
        loginUser.setClientIp(ServletUtils.getClientIP(ServletUtils.getCurrRequest()));
        CacheUtils.USER_CACHE.put(loginUser.getId(), loginUser);
        String sessionId = getSessionId();
        if (StringUtils.isNotBlank(sessionId)) {
            // 重要，模拟登录设置HttpSession
            ServletUtils.getCurrResponse().addCookie(new Cookie("JSESSIONID", getSessionId()));
            CacheUtils.SESSION_CACHE.put(sessionId, loginUser.getId());
        }

        return loginUser;
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
        if (!isExpire()) {
            Object user = StpUtil.getLoginId();
            clearCache();
            StpUtil.logout();
            log.info("logout : {}", user);
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
        if (StringUtils.isBlank(sessionId)) {
            return;
        }

        String userId = CacheUtils.SESSION_CACHE.get(sessionId);
        CacheUtils.SESSION_CACHE.remove(sessionId);
        CacheUtils.USER_CACHE.remove(userId);
    }

    /**
     * 指定账号跳转到登录页
     *
     * @param sessionId HttpSession ID
     */
    public static void toLoginPage(String sessionId) {
        if (StringUtils.isBlank(sessionId)) {
            ServletUtils.sendRedirect("/loginPage");
            return;
        }

        String userId = CacheUtils.SESSION_CACHE.get(sessionId);
        CacheUtils.SESSION_CACHE.remove(sessionId);
        LoginUser user = CacheUtils.USER_CACHE.get(userId);
        CacheUtils.USER_CACHE.remove(userId);
        if (Objects.isNull(user)) {
            ServletUtils.sendRedirect("/loginPage");
            return;
        }
        ResponseResult<String> responseResult = new ResponseResult<>(ResponseCode.AUTHORIZED_EXPIRE, "会话过期！", user.getClientIp());
        // HttpSessionId-loginName[userName] clientIp
        log.info("{}-{}[{}] {}", sessionId, user.getLoginname(), user.getUsername(), responseResult);
        SessionWebSocketServer.sendMsg(JSONUtil.toJsonStr(responseResult), sessionId);
    }

    /**
     * 当前账号跳转到登录页
     */
    public static void toLoginPage() {
        String sessionId = SubjectUtils.getSessionId();
        SubjectUtils.toLoginPage(sessionId);
    }

    /**
     * 判断指定账号是否过期
     *
     * @param userId 用户ID
     */
    public static boolean isExpire(String userId) {
        return StpUtil.getStpLogic().getTokenActiveTimeoutByToken(StpUtil.getTokenValueByLoginId(userId)) == SaTokenDao.NOT_VALUE_EXPIRE;
    }

    /**
     * 判断当前账号是否过期
     */
    public static boolean isExpire() {
        return isExpire(getUserId());
    }

    public static String getUserId() {
        return getUser().getId();
    }

    public static String getUserName() {
        return getUser().getUsername();
    }

}
