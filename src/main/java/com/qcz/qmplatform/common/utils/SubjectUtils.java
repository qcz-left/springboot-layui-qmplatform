package com.qcz.qmplatform.common.utils;

import com.qcz.qmplatform.common.constant.Constant;
import com.qcz.qmplatform.module.system.domain.User;
import com.qcz.qmplatform.module.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.util.ByteSource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * shiro 工具类，主要用于获取session中的当前人信息及设置当前人信息，以及加密方法
 *
 * @author quchangzhong
 */
public class SubjectUtils {

    public static final String PASSWORD_UNCHANGED = "******";

    private static final Map<String, Object> userCache = new ConcurrentHashMap<>();

    public static User getUser() {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal != null) {
            String sign = Constant.CURRENT_USER_SIGN + principal;
            Object user = userCache.get(sign);
            if (user == null) {
                user = SpringContextUtils.getBean(UserService.class).getById(String.valueOf(principal));
                userCache.put(sign, user);
            }
            return (User) user;
        }
        return null;
    }

    public static void setUser(User user) {
        String sign = Constant.CURRENT_USER_SIGN + user.getId();
        Session session = SecurityUtils.getSubject().getSession();
        session.setTimeout(30 * 60 * 1000L);
        userCache.put(sign, user);
    }

    public static String getUserId() {
        return getUser().getId();
    }

    public static String getUserName() {
        return getUser().getUsername();
    }

    public static String md5Encrypt(String name, String password) {
        Object salt = ByteSource.Util.bytes(name);

        Object result = new SimpleHash(Constant.SUBJECT_ALGORITHM_NAME_MD5, password, salt, Constant.SUBJECT_HASHTERATIONS);
        return result.toString();
    }

    /**
     * 密码是否发生变化
     *
     * @param pwd
     */
    public static boolean passwordChanged(String pwd) {
        return !StringUtils.equals(PASSWORD_UNCHANGED, pwd);
    }

}
