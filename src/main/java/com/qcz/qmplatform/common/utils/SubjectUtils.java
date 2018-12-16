package com.qcz.qmplatform.common.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.qcz.qmplatform.common.redis.RedisCache;
import com.qcz.qmplatform.module.sys.entity.User;

/**
 * shiro 工具类，主要用于获取session中的当前人信息及设置当前人信息，以及加密方法
 * @author quchangzhong
 * @time 2018年2月20日 下午8:11:30
 */
public class SubjectUtils {

	@SuppressWarnings("unchecked")
	public static RedisCache<String, Object> getRedisCache() {
		return (RedisCache<String, Object>) SpringContextUtil.getBean(RedisCache.class);
	}

	public static User getUser() {
		RedisCache<String, Object> redisCache = getRedisCache();
		Object currentUser = redisCache.get(Constants.CURRENT_USER_SIGN);
		if (currentUser == null) {
			currentUser = SecurityUtils.getSubject().getPrincipal();
			redisCache.put(Constants.CURRENT_USER_SIGN, currentUser);
		}
		return currentUser == null ? new User() : (User) currentUser;
	}

	public static void setUser(Object user) {
		SecurityUtils.getSubject().getSession().setAttribute(Constants.CURRENT_USER_SIGN, user);
		getRedisCache().put(Constants.CURRENT_USER_SIGN, user);
	}

	public static String getUserId() {
		return getUser().getUserId();
	}

	public static String getUserName() {
		return getUser().getUserName();
	}

	public static String md5Encrypt(String name, String password) {
		Object salt = ByteSource.Util.bytes(name);

		Object result = new SimpleHash(Constants.SUBJECT_ALGORITHM_NAME_MD5, password, salt, Constants.SUBJECT_HASHTERATIONS);
		return result.toString();
	}

}
