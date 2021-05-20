package com.qcz.qmplatform.common.utils;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import com.qcz.qmplatform.common.constant.Constant;
import com.qcz.qmplatform.module.system.domain.User;
import com.qcz.qmplatform.module.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * shiro 工具类，主要用于获取session中的当前人信息及设置当前人信息，以及加密方法
 *
 * @author quchangzhong
 */
public class SubjectUtils {

    public static final String PASSWORD_UNCHANGED = "******";

    private static final TimedCache<String, User> userCache = CacheUtils.USER_CACHE;

    private static final AES AES = SecureUtil.aes(SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded());

    private static final DES DES = SecureUtil.des();

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
        CacheUtils.SESSION_CACHE.put(SecurityUtils.getSubject().getSession().getId().toString(), user);
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
     * @param pwd the password
     */
    public static boolean passwordChanged(String pwd) {
        return !StringUtils.equals(PASSWORD_UNCHANGED, pwd);
    }

}
