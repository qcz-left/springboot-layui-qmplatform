package com.qcz.qmplatform.common.utils;

/**
 * 常量设置
 * @author changzhongq
 * @time 2018年6月22日 下午6:29:18
 */
public class Constants {

	public static final String SUBJECT_ALGORITHM_NAME_MD5 = "MD5";// 加密算法

	public static final int SUBJECT_HASHTERATIONS = 0x400;// 加密次数1024

	public static final String SHIRO_REDIS_NAME = "shiro_redis";

	public static final String CURRENT_USER_SIGN = "currentUser";// 当前登录人标识

	public static final String DEFAULT_USER_LOGIN_PASSWORD = "12345678";// 默认的用户登录密码

	/*********************** rabbitmq 相关 ******************************/
	public static final String EXCHANGE_NAME = "demoExchange";

	public static final String QUEUE_NAME = "demoQueue";
	
	public static final String RABBIT_ROUTING_KEY = "demo-routing-key";
}
